package com.goods.contorller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.model.service.CartIteamService;
import com.store.model.service.StoreService;

@WebServlet("/cart/Write")
public class WriteCartServlet extends HttpServlet {
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doPost(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        response.setCharacterEncoding("UTF-8");
	        CartIteamService cartSvc = new  CartIteamService();
	        StoreService storeSvc = new StoreService();
	        Writer out = response.getWriter();
	        String userId = String.valueOf(request.getSession().getAttribute("memId"));
	        String cartJson = cartSvc.getCart(userId);
	        out.write(cartJson);
	    }
}
