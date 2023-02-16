package com.goods.model.Cart.pojo;

import java.util.HashMap;

public  class Cart implements java.io.Serializable {

	private String userId;// 會員編號
	private final HashMap<Integer, HashMap<Integer, CartItem>> storeMap = new HashMap();// 所有會員各店家的購物車
	private Integer CartsTotalPrice;// 所有購物車的總金額

	public Cart() {}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getCartsTotalPrice() {
		return CartsTotalPrice;
	}

	public void setCartsTotalPrice(Integer cartsTotalPrice) {
		CartsTotalPrice = cartsTotalPrice;
	}

	public HashMap<Integer, HashMap<Integer,CartItem >> getStoreMap() {
		return storeMap;
	}

	
}