package com.order.contorller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.order.model.OrderDetail.pojo.OrderDetail;
import com.order.model.service.OrderDetailService;

@WebServlet("/orderDetail.do")

public class OrderDetailServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// 來自Member_goods_listAll.jsp的請求
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("orderId");

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Member/order/Member_order_listAll.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}
			Integer orderId = null;
			try {
				orderId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("商品編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Member/goods/Member_goods_listAll.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			OrderDetailService orderDetailSvc = new OrderDetailService();
			List<OrderDetail> orderDetailList = orderDetailSvc.getAll(orderId);
			if (orderDetailList == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/Member/order/Member_order_listAll.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("orderDetailList", orderDetailList); // 資料庫取出的goods物件,存入req
			String url = "/front-end/Member/order/Member_order_listDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneGoods.jsp
			successView.forward(req, res);
		}
	}

}
