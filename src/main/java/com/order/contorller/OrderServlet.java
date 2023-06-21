package com.order.contorller;

import java.io.IOException;
import java.io.Serial;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.goods.model.Cart.pojo.Cart;
import com.goods.model.Goods.dao.impl.GoodsJDBCDAO;
import com.goods.model.Goods.pojo.Goods;
import com.goods.model.service.CartIteamService;
import com.goods.model.service.GoodsService;
import com.order.model.Order.dao.impl.OrderJDBCDAO;
import com.order.model.Order.pojo.Order;
import com.order.model.OrderDetail.pojo.OrderDetail;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;

@WebServlet("/order/order.do")
@MultipartConfig
public class OrderServlet extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;
	public static AllInOne domain;
	

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getMemId_For_Display_Order".equals(action)) {
			System.out.println(123);
			HttpSession session = req.getSession();
			Integer memId = (Integer) session.getAttribute("memId");

			req.setAttribute("memId", memId);
			System.out.println("抓到" + memId);
			String url = "/front-end/Member/order/Member_order_listAll.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("checkout".equals(action)) { // 來自Member_cart.jsp的請求將redis取的值轉交checkout
			HttpSession session = req.getSession();
			Integer memId = (Integer) session.getAttribute("memId");
			req.setAttribute("memId", memId);
			session = req.getSession();
			Integer storeId = (Integer) session.getAttribute("storeId");
			req.setAttribute("storeId", storeId);
			String[] goodsId = req.getParameterValues("goodsId");
			String[] goodsPrice = req.getParameterValues("goodsPrice");
			String[] detailQuantity = req.getParameterValues("detailQuantity");
			String[] goodsName = req.getParameterValues("goodsName");

			System.out.println("跑進來此checkout action : 125");
			// 轉換陣列型態
			int[] storeIdInt = new int[goodsId.length];
			int[] goodsIdInt = new int[goodsId.length];
			int[] goodsPriceInt = new int[goodsId.length];
			int[] detailQuantityInt = new int[goodsId.length];

			GoodsService goodsSvc;
			Goods goods = null;

			List<Goods> goodsList = new LinkedList<Goods>();

			for (int i = 0; i < goodsIdInt.length; i++) {

				goodsIdInt[i] = Integer.parseInt(goodsId[i]);
				goodsPriceInt[i] = Integer.parseInt(goodsPrice[i]);
				detailQuantityInt[i] = Integer.parseInt(detailQuantity[i]);
				goodsSvc = new GoodsService();
				goods = goodsSvc.getOneGoods(goodsIdInt[i]);
				goodsList.add(goods);
			}
			if (goods == null) {
			}
			/*************************** 傳值至 Member_order_checkout.jsp *************/
			else {

				req.setAttribute("storeId", storeId);
				req.setAttribute("goodsList", goodsList);
				req.setAttribute("goodsIdInt", goodsIdInt);
				req.setAttribute("goodsPriceInt", goodsPriceInt);
				req.setAttribute("goodsName", goodsName);
				req.setAttribute("detailQuantityInt", detailQuantityInt);
				req.setAttribute("memId", memId);
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Member/order/Member_order_checkout.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

		}
		//確認訂單項目之後 轉跳綠界刷卡
		if ("ecpay".equals(action)) {
			// 根據表單建立收款連結 (中文編碼有問題)
			// 使用者跳轉至綠界的交易流程網站
			// 按照流程輸入卡號..... (中文編碼!)
			// 測試卡號: 一般信用卡測試卡號 : 4311-9522-2222-2222 安全碼 : 222
			// 信用卡測試有效月/年：輸入的 MM/YYYY 值請大於現在當下時間的月年，
			// 例如在 2016/04/20 當天作測試，請設定 05/2016(含)之後的有效月年，否則回應刷卡失敗。
			// 手機請輸入正確，因為會傳驗證碼
			// 檢查後台: 信用卡收單 - 交易明細 - 查詢
			domain = new AllInOne("");
			AioCheckOutOneTime obj = new AioCheckOutOneTime();
			// 從 view 獲得資料，依照 https://developers.ecpay.com.tw/?p=2866 獲得必要的參數
			// MerchantTradeNo : 必填 特店訂單編號 (不可重複，因此需要動態產生)
			obj.setMerchantTradeNo(new String("salon" + System.currentTimeMillis()));
			// MerchantTradeDate : 必填 特店交易時間 yyyy/MM/dd HH:mm:ss
			obj.setMerchantTradeDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date()));
			// TotalAmount : 必填 交易金額
			String orderFprice = req.getParameter("orderFprice");
			Integer money = Integer.valueOf(orderFprice).intValue();
			obj.setTotalAmount(String.valueOf(money));
			// TradeDesc : 必填 交易描述
			String storeId = req.getParameter("storeId");
			Integer sID = Integer.valueOf(storeId).intValue();
			obj.setTradeDesc("StoreID:" + sID);
			// ItemName : 必填 商品名稱
			obj.setItemName("FoodMap Buy Goods");
			// ReturnURL : 必填 我用不到所以是隨便填一個英文字
			obj.setReturnURL("a");
			// OrderResultURL : 選填 消費者完成付費後。重新導向的位置
			System.out.println("透過這裡跳checkout action");
			String url = "http://" + req.getServerName() + ":8080/CGA105G2/order/order.do?action=orderSuccess";

			obj.setClientBackURL(url);
			obj.setNeedExtraPaidInfo("N");
			// 回傳form訂單 並自動將使用者導到 綠界
			String form = domain.aioCheckOut(obj, null);
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print("<html><body>" + form + "</body></html>");

	            // 設定生成訂單&明細資料給 "chackout"
	            String[] goodsId = req.getParameterValues("goodsId");
				String[] goodsPrice = req.getParameterValues("goodsPrice");
				String[] detailQuantity = req.getParameterValues("detailQuantity");
				String[] goodsName = req.getParameterValues("goodsName");
	            
	            HttpSession session = req.getSession();
	            session.setAttribute("goodsId", goodsId);
	            session.setAttribute("goodsPrice", goodsPrice);
	            session.setAttribute("detailQuantity", detailQuantity);
	            session.setAttribute("goodsName", goodsName);

		}
		
		//綠界刷卡完成後生成訂單&明細
		if ("orderSuccess".equals(action)) { // 來自Member_order_checkout.jsp的請求
			HttpSession session = req.getSession();
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer memId = (Integer) req.getSession().getAttribute("memId");
			List<String> listG = List.of((String []) session.getAttribute("goodsId"));

			if (listG.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member/goods/Member_cart.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
			/*************************** 2.開始新增資料 ***************************************/
			List<String> listQ = List.of((String []) session.getAttribute("detailQuantity"));

			Order order = new Order();
			order.setMemId(memId);

			Map<Integer, List<OrderDetail>> ordersid = new LinkedHashMap<Integer, List<OrderDetail>>();

			List<String> Allsid = new ArrayList<>();

			for (Integer i = 0; i < listG.size(); i++) {

				Integer goodid = Integer.valueOf(listG.get(i));
				GoodsJDBCDAO goodsJDBCDAO = new GoodsJDBCDAO();

				Goods vo = goodsJDBCDAO.getById(goodid);

				Integer goodp = vo.getGoodsPrice();
				Integer sid = vo.getStoreId();

				Allsid.add(String.valueOf(sid));

				Integer goodq = Integer.valueOf(listQ.get(i));

				OrderDetail odvo = new OrderDetail();
				odvo.setGoodsId(goodid);
				odvo.setDetailPrice(goodp);
				odvo.setDetailQuantity(goodq);

				if (ordersid.get(sid) == null) {
					List<OrderDetail> orderDetails = new ArrayList<>();
					orderDetails.add(odvo);
					ordersid.put(sid, orderDetails);
				} else {
					ordersid.get(sid).add(odvo);
				}
			}
			// 去重複
			Set<String> set = new LinkedHashSet<>(Allsid);
			Allsid = new ArrayList<>(set);
			for (Integer i = 0; i < Allsid.size(); i++) {
				Integer thissid = Integer.valueOf(Allsid.get(i));
				Integer sidbuytallprice = 0;
				List<OrderDetail> sidtobuy = ordersid.get(thissid);

				for (OrderDetail a : sidtobuy) {
					sidbuytallprice += (a.getDetailPrice() * a.getDetailQuantity());
				}
				Order orderto = new Order();
				orderto.setMemId(memId);
				orderto.setStoreId(thissid);
				orderto.setOrderPrice(sidbuytallprice);
//				先以null處理
				orderto.setOrderFre(80);
				orderto.setOrderFprice(sidbuytallprice - 80);
//				狀態
				orderto.setOrderStatus(0);
				new OrderJDBCDAO().insertWithDetail(orderto, sidtobuy);
				/*************************** 4.新增完成,刪除Redis資料 ***********/
				final Integer storeId2 = thissid;
				String userId = String.valueOf(memId);
				CartIteamService cartSvc = new CartIteamService();
				Cart cart = cartSvc.get(userId);
//				迴圈
				for (OrderDetail j : sidtobuy) {
					cart.getStoreMap().get(storeId2).remove(j.getGoodsId());
				}
				cartSvc.put(storeId2, cart);

			}
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			req.setAttribute("memId", memId);
			String url = "/front-end/Member/order/Member_order_listAll.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllGoods.jsp
			successView.forward(req, res);
		}
	}
}
