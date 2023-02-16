package com.goods.model.Cart.dao;

import com.goods.model.Cart.pojo.Cart;

public interface CartItemDAO_interface {

	public void put(Integer storeId, Cart cart);

	public Cart get(String memId);

	public String getCart(String memId);

	public void clear(String memId);

	public void reduceQty(String memId, Integer storeId, Integer goodsId);

	public void addQty(String memId, Integer storeId, Integer goodsId);

//    public void putInDb(String memId, Integer storeId);

}
