package com.goods.model.Cart.pojo;

public class CartItem {
	
		private static final long serialVersionUID = 1L;

		private Integer storeId;//店家編號
		private String storeName;//店家名稱
		private Integer goodsId; // 商品編號
		private String goodsName;// 商品名稱
		private Integer goodsPrice;// 商品價格
		private Integer detailQuantity; // 商品數量
		private Integer goodsTotalPrice;//商品總金額

		public CartItem() {

		}

		public Integer getStoreId() {
			return storeId;
		}

		public void setStoreId(Integer storeId) {
			this.storeId = storeId;
		}

		public String getStoreName() {
			return storeName;
		}

		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}

		public Integer getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(Integer goodsId) {
			this.goodsId = goodsId;
		}

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public Integer getGoodsPrice() {
			return goodsPrice;
		}

		public void setGoodsPrice(Integer goodsPrice) {
			this.goodsPrice = goodsPrice;
		}

		public Integer getDetailQuantity() {
			return detailQuantity;
		}

		public void setDetailQuantity(Integer detailQuantity) {
			this.detailQuantity = detailQuantity;
		}

		public Integer getGoodsTotalPrice() {
			return goodsTotalPrice;
		}

		public void setGoodsTotalPrice(Integer goodsTotalPrice) {
			this.goodsTotalPrice = goodsTotalPrice;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		
}
