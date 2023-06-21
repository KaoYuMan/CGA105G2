package com.order.model.OrderDetail.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.goods.model.Goods.pojo.Goods;
import com.goods.model.service.GoodsService;
import com.order.model.Order.pojo.Order;
import com.order.model.service.OrderService;

@Entity
@IdClass(OrderDetail_PK.class)
@Table(catalog = "cga105g2", name = "order_detail")

public class OrderDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ORDER_ID")
	private Integer orderId;
	@Id
	@Column(name = "GOODS_ID")
	private Integer goodsId;
	@Column(name = "DETAIL_QUANTITY")
	private Integer detailQuantity;
	@Column(name = "DETAILPRICE")
	private Integer detailPrice;
	

	public OrderDetail() {
	}

	public OrderDetail( Integer goodsId,Integer detailQuantity,Integer detailPrice) {
		
		this.goodsId = goodsId;
	    this.detailQuantity = detailQuantity;
		this.detailPrice = detailPrice;
	}

	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getDetailQuantity() {
		return detailQuantity;
	}

	public void setDetailQuantity(Integer detailQuantity) {
		this.detailQuantity = detailQuantity;
	}

	public Integer getDetailPrice() {
		return detailPrice;
	}

	public void setDetailPrice(Integer detailPrice) {
		this.detailPrice = detailPrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Goods getGoods() {
		GoodsService goodsSvc = new GoodsService();
		Goods goods = goodsSvc.getOneGoods(goodsId);
		return goods;
	}

	public Order getOrder() {
		OrderService ordersSvc = new OrderService();
		Order ordersVO = ordersSvc.getOneOrder(orderId);
		return ordersVO;
	}

}
