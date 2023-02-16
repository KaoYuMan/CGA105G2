package com.goods.model.Cart.dao;

import com.goods.model.Cart.pojo.Cart;

public interface CacheDAO_interface {
	public void put(Cart cart);

	public Cart get(String memId);

	public String getCart(String memId);

	public void clear(String memId);

}
