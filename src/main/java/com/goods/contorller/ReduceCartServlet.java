package com.goods.contorller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.model.Cart.pojo.Cart;
import com.goods.model.Cart.pojo.CartItem;
import com.goods.model.service.CartIteamService;
import com.store.model.service.StoreService;

@WebServlet("/cart/reduce")
public class ReduceCartServlet extends HttpServlet {
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doPost(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

	        final Integer storeId = Integer.valueOf(request.getParameter("storeId"));
	        final Integer goodsId = Integer.valueOf(request.getParameter("goodsId"));
	        String userId = String.valueOf(request.getSession().getAttribute("memId"));
	        CartIteamService cartSvc = new CartIteamService();
	        StoreService storeService = new StoreService();
	        String storeName = storeService.getById(storeId).getStoreName();
	        Cart cart = cartSvc.get(userId);
	        CartItem cartItem = new CartItem();
	        HashMap<Integer, CartItem> cartItemMap;
	        //判斷有沒有購物車
	        if (cart != null) {
	            //判斷有沒有買過該商店
	            if (cart.getStoreMap().get(storeId) != null) {
	                //判斷有沒有買過該商品
	                if (cart.getStoreMap().get(storeId).get(goodsId) != null) {
	                    cartSvc.reduceQty(userId, storeId, goodsId);
	                    //沒買過該商品
	                } else {
	                    cartItem = new CartItem();
	                    cartItem.setGoodsId(goodsId);
	                    cartItem.setDetailQuantity(1);
	                    cart.getStoreMap().get(storeId).put(cartItem.getGoodsId(), cartItem);
	                    cartSvc.put(storeId, cart);
	                }
	                //有買過東西但沒買過該商店
	            } else {
	                cartItemMap = new HashMap();
	                cartItem.setStoreId(storeId);
	                cartItem.setStoreName(storeName);
	                cartItem.setGoodsId(goodsId);
	                cartItem.setDetailQuantity(1);
	                cartItemMap.put(cartItem.getGoodsId(), cartItem);
	                cart.getStoreMap().put(cartItem.getStoreId(), cartItemMap);
	                cartSvc.put(cartItem.getStoreId(), cart);
	            }
	            //完全沒買過東西
	        } else {
	            cart = new Cart();
	            cartItemMap = new HashMap();
	            cart.setUserId(userId);
	            cartItem.setStoreId(storeId);
	            cartItem.setStoreName(storeName);
	            cartItem.setGoodsId(goodsId);
	            cartItem.setDetailQuantity(1);
	            cartItemMap.put(cartItem.getGoodsId(), cartItem);
	            cart.getStoreMap().put(cartItem.getStoreId(), cartItemMap);
	            cartSvc.put(cartItem.getStoreId(), cart);
	        }
	    }
}
