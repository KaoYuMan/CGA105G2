package com.goods.model.service;

import java.util.List;
import java.util.Map;

import com.goods.model.Goods.dao.GoodsDAO_interface;
import com.goods.model.Goods.dao.impl.GoodsJDBCDAO;
import com.goods.model.Goods.pojo.Goods;


public class GoodsService {

	private GoodsDAO_interface dao;

	public GoodsService() {
		dao = new GoodsJDBCDAO(); 
	}
	
	public Goods addGoods(Integer storeId,byte[] goodsImg,String goodsName,Integer goodsStatus,Integer goodsPrice,String goodsText) {
		Goods goods = new Goods();
		
		goods.setStoreId(storeId);
		goods.setGoodsImg(goodsImg);
		goods.setGoodsName(goodsName);
		goods.setGoodsStatus(goodsStatus);
		goods.setGoodsPrice(goodsPrice);
		goods.setGoodsText(goodsText);
		dao.insert(goods);
		
		return goods;
	}
	public Goods updateGoods(Integer goodsId, Integer storeId, byte[] goodsImg, String goodsName, Integer goodsStatus, Integer goodsPrice, String goodsText) {
		Goods goods = new Goods();
		
		goods.setGoodsId(goodsId);
		goods.setStoreId(storeId);
		goods.setGoodsImg(goodsImg);
		goods.setGoodsName(goodsName);
		goods.setGoodsStatus(goodsStatus);
		goods.setGoodsPrice(goodsPrice);
		goods.setGoodsText(goodsText);
//		goods.setGoodsTime(goodsTime);
//		goods.setGoodsRtime(goodsRtime);
		dao.update(goods);
		return goods;
	}
	public void deleteGoods(Integer goodsno) {
		dao.delete(goodsno);
	}
	public Goods getOneGoods(Integer goodsno) {
		return dao.getById(goodsno);
	}
	public List<Goods> getAll(Integer storeno) {
		return dao.getAll(storeno);
	}
	public List<Goods> getAllGoods(Map<String, String[]> map) {
		return dao.getAllGoods(map);
	}
}