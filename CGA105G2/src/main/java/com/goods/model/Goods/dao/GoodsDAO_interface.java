package com.goods.model.Goods.dao;

import com.goods.model.Goods.pojo.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsDAO_interface {
	public void insert(Goods goods);

	public void update(Goods goods);

	public void delete(Integer goodsno);

	public Goods getById(Integer goodsno);

	public List<Goods> getAll(Integer storeno);

	public List<Goods> getAllGoods(Map<String, String[]> map);
}
