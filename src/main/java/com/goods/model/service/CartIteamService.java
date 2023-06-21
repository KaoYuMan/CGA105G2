package com.goods.model.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import com.goods.model.Cart.dao.CacheDAO_interface;
import com.goods.model.Cart.dao.CartItemDAO_interface;
import com.goods.model.Cart.pojo.Cart;
import com.goods.model.Cart.pojo.CartItem;
import com.goods.model.Goods.dao.GoodsDAO_interface;
import com.goods.model.Goods.dao.impl.GoodsJDBCDAO;
import com.goods.model.Goods.pojo.Goods;
import com.store.model.service.StoreService;

public class CartIteamService implements CartItemDAO_interface {

	private final CacheDAO_interface cartCacheDao = new CacheService();

	private final GoodsDAO_interface goodsDao = new GoodsJDBCDAO();

	 @Override
	    public void put(Integer storeId, Cart cart) {
	        AtomicInteger storeTotalPrc = new AtomicInteger();
	        cart.getStoreMap().get(storeId).forEach((goodsId, cartItem) -> {
	            Goods product = goodsDao.getById(goodsId);
	            StoreService storeService = new StoreService();
	            String storeName = storeService.getById(storeId).getStoreName();
	            cartItem.setStoreName(storeName);
	            cartItem.setStoreId(storeId);
	            cartItem.setGoodsName(product.getGoodsName());
	            cartItem.setGoodsPrice(product.getGoodsPrice());
	            cartItem.setGoodsTotalPrice(product.getGoodsPrice() * cartItem.getDetailQuantity());
	        });
	        cart.getStoreMap().forEach((k,v)->v.forEach((k1,v2)->storeTotalPrc.getAndAdd(v2.getGoodsTotalPrice())));
	        cart.setCartsTotalPrice(storeTotalPrc.get());
	        cartCacheDao.put(cart);
	    }

	    @Override
	    public Cart get(String userId) {
	        Cart cart = cartCacheDao.get(userId);
	        return cart;
	    }
	    @Override
	    public String getCart(String userId) {
	        String cartJson = cartCacheDao.getCart(userId);
	        return cartJson;
	    }

	    @Override
	    public void clear(String userId) {
	        cartCacheDao.clear(userId);
	    }


	    @Override
	    public void reduceQty(String userId, Integer storeId, Integer goodsId) {
	        Cart cart = get(userId);
	        CartItem cartItem = cart.getStoreMap().get(storeId).get(goodsId);
	        Integer cartItemQty = cartItem.getDetailQuantity()-1;
	        if(cartItemQty == 0){
	            cart.getStoreMap().get(storeId).remove(cartItem.getGoodsId());
	        }else {
	            cartItem.setDetailQuantity(cartItemQty);
	        }

	        put(storeId, cart);
	    }

	    @Override
	    public void addQty(String userId, Integer storeId, Integer goodsId) {
	        Cart cart = get(userId);
	        CartItem cartItem = cart.getStoreMap().get(storeId).get(goodsId);
	        cartItem.setDetailQuantity(cartItem.getDetailQuantity() + 1);
	        put(storeId, cart);
	    }


	}
