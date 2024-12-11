package com.example.redis;

import redis.clients.jedis.UnifiedJedis;

public class ExternalCounter {
	private String key;
	private UnifiedJedis redis;
	
	public ExternalCounter(String key, UnifiedJedis redis) {
		this.key = key;
		this.redis = redis;
	}

	public long get() {
		try {
			return Long.parseLong(redis.get(key));
		} catch (NumberFormatException ex) {
			reset();
			return 0;
		}
	}

	public void set(long value) {
		redis.set(key, Long.toString(value));
	}

	public long increment() {
		return redis.incr(key);
	}

	public long decrement() {
		return redis.decr(key);
	}

	public void reset() {
		redis.set(key, "0");
	}

}
