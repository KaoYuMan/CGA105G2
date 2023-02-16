package com.goods.model.Cart.pojo;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
	// JedisPool
	private static JedisPool pool = null;

	private JedisUtil() {
	}

	public static JedisPool getJedisPool() {
		// double lock
		if (pool == null) {
			synchronized (JedisUtil.class) {
				if (pool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					// 控制連線池裡面最大連接數(能有多少 Jedis 實體)
					config.setMaxTotal(30);
					// 空閒最大連接數
//					config.setMaxIdle(8);
					// 最大阻塞時間(毫秒)
					config.setMaxWaitMillis(10000);
					// 創建連線池
					pool = new JedisPool(config, "localhost", 6379);
				}
			}
		}
		return pool;
	}

	public static void shutdownJedisPool() {
		if (pool != null)
			pool.destroy();
	}

}
