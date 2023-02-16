package com.goods.model.service;

import org.springframework.stereotype.Service;
import com.goods.model.Cart.dao.CacheDAO_interface;
import com.goods.model.Cart.pojo.Cart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import redis.clients.jedis.Jedis;

@Service
public class CacheService implements CacheDAO_interface {

	final Jedis jedis = new Jedis("localhost");
	final Gson gson = new GsonBuilder().create();

	@Override
	public void put(Cart cart) {

		jedis.set(cart.getUserId(), gson.toJson(cart));

	}

	@Override
	public Cart get(String userId) {
		return gson.fromJson(jedis.get(userId), Cart.class);

	}

	@Override
	public String getCart(String userId) {
		return jedis.get(userId);

	}

	@Override
	public void clear(String userId) {
		jedis.del(userId);
	}

	public static void main(String[] args) {
		CacheService dao = new CacheService();
	}

}
