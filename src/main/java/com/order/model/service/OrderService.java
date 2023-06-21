package com.order.model.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.code.model.Code.pojo.Code;
import com.goods.model.Cart.pojo.Cart;
import com.order.model.Order.dao.OrderDAO_interface;
import com.order.model.Order.dao.impl.OrderJDBCDAO;
import com.order.model.Order.pojo.Order;
import com.order.model.OrderDetail.pojo.OrderDetail;

public class OrderService {
	private OrderDAO_interface dao;

	public OrderService() {
		dao = new OrderJDBCDAO();
	}

	public void addOneOrder(Order order, List<OrderDetail> list) {
		dao.insertWithDetail(order, list);
	}

	OrderDetailService orderDetailService = new OrderDetailService();

	public void addOrder(Cart cart) {
		cart.getStoreMap().forEach((storeId, cartItemMap) -> {
			Order order = new Order();
			order.setMemId(Integer.valueOf(cart.getUserId()));
			order.setStoreId(Integer.valueOf(storeId));
			AtomicInteger storeTotalPrc = new AtomicInteger();
			cartItemMap.forEach((goodsId, cartItem) -> {
				storeTotalPrc.getAndAdd(cartItem.getGoodsTotalPrice());
			});
			order.setOrderFprice(storeTotalPrc.get());
			order.setOrderTime(new Timestamp(System.currentTimeMillis()));
			order.setOrderStatus(1);
			dao.insert(order);
			Integer orderId = dao.genOrderId();
			cartItemMap.forEach((goodsId, cartItem) -> {
				orderDetailService.addOrderDetail(new OrderDetail(orderId, goodsId, cartItem.getDetailQuantity()));
			});
		});

	}

	public List<Order> getByMemId(Integer memId) {
		return dao.getByMemId(memId);
	}

	public Order getOneOrder(Integer ordId) {
		return dao.getById(ordId);
	}

	public Set<Order> getOrderByStoreId(Integer storeId) {
		return dao.getOrderByStoreId(storeId);
	}

	public Order updateOrdStat(Integer ordId, Integer ordStat) {

		Order order = new Order();

		order.setOrderId(ordId);
		order.setOrderStatus(ordStat);
		dao.updateOrdStat(order);

		return order;
	}

	public Code checkCodeDiscount(String codeNum) {
		return dao.checkCodeDiscount(codeNum);


	}
}
