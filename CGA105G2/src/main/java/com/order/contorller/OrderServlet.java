package com.order.contorller;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foodorder.model.service.FoodorderService;
import com.goods.model.Cart.pojo.Cart;
import com.goods.model.Goods.dao.impl.GoodsJDBCDAO;
import com.goods.model.Goods.pojo.Goods;
import com.goods.model.service.CartIteamService;
import com.goods.model.service.GoodsService;
import com.order.model.Order.dao.impl.OrderJDBCDAO;
import com.order.model.Order.pojo.Order;
import com.order.model.OrderDetail.pojo.OrderDetail;
import com.order.model.service.OrderDetailService;
import com.order.model.service.OrderService;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/order/order.do")
@MultipartConfig
public class OrderServlet extends HttpServlet {

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
		    System.out.println("抓到"+ memId);
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
		FoodorderService foodorderSvc = new FoodorderService();
		if ("orderSuccess".equals(action)) { // 來自Member_order_checkout.jsp的請求
			HttpSession session = req.getSession();
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			Integer memId = (Integer) req.getSession().getAttribute("memId");
			List<String> listG= List.of(req.getParameterValues("goodsId"));


			if (listG.isEmpty()){
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Member/goods/Member_cart.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}
			/*************************** 2.開始新增資料 ***************************************/
			List<String> listQ= List.of(req.getParameterValues("detailQuantity"));

			List<OrderDetail> Olist=null;
			Order order=new Order();
			order.setMemId(memId);

			Map<Integer,List<OrderDetail>> ordersid = new LinkedHashMap<Integer,List<OrderDetail>>();

			List<String> Allsid=new ArrayList<>();
			List<Integer> goodidlist = new ArrayList();

			for(Integer i=0;i<listG.size();i++){


				Integer goodid= Integer.valueOf(listG.get(i));
				GoodsJDBCDAO goodsJDBCDAO=new GoodsJDBCDAO();

				Goods vo=goodsJDBCDAO.getById(goodid);

				Integer goodp=vo.getGoodsPrice();
				Integer sid=vo.getStoreId();


				Allsid.add(String.valueOf(sid));

				Integer goodq=Integer.valueOf(listQ.get(i));

				OrderDetail odvo=new OrderDetail();
				odvo.setGoodsId(goodid);
				odvo.setDetailPrice(goodp);
				odvo.setDetailQuantity(goodq);

				if (ordersid.get(sid)==null){
					List<OrderDetail> orderDetails=new ArrayList<>();
					orderDetails.add(odvo);
					ordersid.put(sid,orderDetails);
				}else {
					ordersid.get(sid).add(odvo);
				}
			}
			// 去重複
			Set<String> set = new LinkedHashSet<>(Allsid);
			Allsid = new ArrayList<>(set);
			for(Integer i=0;i<Allsid.size();i++){
				Integer thissid= Integer.valueOf(Allsid.get(i));
				Integer sidbuytallprice=0;
				List<OrderDetail> sidtobuy=ordersid.get(thissid);

				for(OrderDetail a : sidtobuy){
					sidbuytallprice+=(a.getDetailPrice()*a.getDetailQuantity());
				}
				Order orderto=new Order();
				orderto.setMemId(memId);
				orderto.setStoreId(thissid);
				orderto.setOrderPrice(sidbuytallprice);
//				先以null處理
				orderto.setOrderFre(80);
				orderto.setOrderFprice(sidbuytallprice-80);
//				狀態
				orderto.setOrderStatus(0);
				new OrderJDBCDAO().insertWithDetail(orderto,sidtobuy);
				/*************************** 4.新增完成,刪除Redis資料 ***********/
				final Integer storeId2 = thissid;
				String userId = String.valueOf(memId);
				CartIteamService cartSvc = new CartIteamService();
				Cart cart = cartSvc.get(userId);
//				迴圈
				for (OrderDetail j :sidtobuy){
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
