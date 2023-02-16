package com.order.model.Order.dao;

import java.util.List;
import java.util.Set;

import com.code.model.Code.pojo.Code;
import com.order.model.Order.pojo.Order;
import com.order.model.OrderDetail.pojo.OrderDetail;

public interface OrderDAO_interface {
	public void insert(Order order);

	public void update(Order order);

	public void delete(Integer orderId);

	public Order getById(Integer orderId);

	public List<Order> getByMemId(Integer memId);

	public Set<Order> getOrderByStoreId(Integer storeId);
	
	public void updateOrdStat(Order order);
	 //同時新增訂單與訂單明細 
	public void insertWithDetail(Order order , List<OrderDetail> list);

	public Integer genOrderId();
	
	//查詢優惠券是否有效以及折扣為多少
	public Code checkCodeDiscount(String codeNum);
	
}