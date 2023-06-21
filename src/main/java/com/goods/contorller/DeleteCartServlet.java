package com.goods.contorller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.model.Cart.pojo.Cart;
import com.goods.model.service.CartIteamService;

@WebServlet("/cart/delete")
public class DeleteCartServlet  extends HttpServlet{
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doPost(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        final Integer storeId = Integer.valueOf(request.getParameter("storeId"));
	        final Integer goodsId = Integer.valueOf(request.getParameter("goodsId"));
	        String userId = String.valueOf(request.getSession().getAttribute("memId"));
	        CartIteamService cartSvc = new  CartIteamService();
	        Cart cart = cartSvc.get(userId);
	        cart.getStoreMap().get(storeId).remove(goodsId);
	        cartSvc.put(storeId, cart);
	    }

}
