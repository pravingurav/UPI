package com.ultracash.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPersistence {

	
	private static final Logger logger = LoggerFactory
			.getLogger(RedisPersistence.class);
	private static RedisPersistence INSTANCE;

	
	private RedisPersistence() {
	}

	public static RedisPersistence getInstance() {
		if (INSTANCE == null) {
			synchronized (RedisPersistence.class) {
				if (INSTANCE == null) {
					INSTANCE = new RedisPersistence();
				}
			}
		}
		return INSTANCE;
	}

	private static JedisPool pool;

	private static void init() {
		if (pool == null)
			synchronized (RedisPersistence.class) {
				if (pool == null)
					pool = new JedisPool(new JedisPoolConfig(), "localhost",
							6379);
			}
	}

	public void putString(String key, String value) {
		init();
		logger.info("putting key-value:"+key+value);
		try (Jedis jedis = pool.getResource()) {
			jedis.expire(key, 100);
			jedis.set(key, value);
		}
	}

	public void putObject(String key, Object value) {
		try {
			throw new Exception("NOT IMPLEMENTED");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getObject(String value) {
		try {
			throw new Exception("NOT IMPLEMENTED");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getString(String key) {
		
		init();
		try (Jedis jedis = pool.getResource()) {
			String value= jedis.get(key);
			logger.info("getting key-value:"+key+value);
			return value;
		}
	}

	public String removeString(String key) {
		init();
		try (Jedis jedis = pool.getResource()) {
			String val = jedis.get(key);
			jedis.del(key);
			return val;
		}
	}

	public Object removeObject(String key) {
		return null;
	}
}
