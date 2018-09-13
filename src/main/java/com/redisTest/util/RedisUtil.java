package com.redisTest.util;

import java.util.Properties;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.liang.util.PropertiesUtil;

/**
 * Redis 工具类
 */
public class RedisUtil {

	protected static Logger logger = Logger.getLogger(RedisUtil.class);
	// Redis服务器IP
	private static String HOST = "127.0.0.1";
	// Redis的端口号
	private static int PORT = 6379;
	// 访问密码
	private static String PASSWORD = null;
	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1000;
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 100;
	private static int MIN_IDLE = 8;
	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 100000;
	// 0是关闭此设置
	private static int TIMEOUT = 300;
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	static {
		Properties prop = PropertiesUtil.getPropertiesFile("redis.properties");
		HOST = prop.getProperty("HOST");
		String str = prop.getProperty("PORT");
		PORT = str != null ? Integer.parseInt(str) : 6379;
		PASSWORD = prop.getProperty("PASSWORD");
		str = prop.getProperty("MAX_ACTIVE");
		if (str != null) {
			MAX_ACTIVE = Integer.parseInt(str);
		}
		str = prop.getProperty("MAX_IDLE");
		if (str != null) {
			MAX_IDLE = Integer.parseInt(str);
		}
		str = prop.getProperty("MIN_IDLE");
		if (str != null) {
			MIN_IDLE = Integer.parseInt(str);
		}
		str = prop.getProperty("MAX_WAIT");
		if (str != null) {
			MAX_WAIT = Integer.parseInt(str);
		}
		str = prop.getProperty("TIMEOUT");
		if (str != null) {
			TIMEOUT = Integer.parseInt(str);
		}
	}

	/**
	 * 初始化Redis连接池
	 */
	private static synchronized void poolInit() {
		if (jedisPool == null) {
			try {
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(MAX_ACTIVE);
				config.setMaxIdle(MAX_IDLE);
				config.setMinIdle(MIN_IDLE);
				config.setMaxWaitMillis(MAX_WAIT);
				config.setTestOnBorrow(TEST_ON_BORROW);

				jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT, PASSWORD);
				System.out.println("HOST:" + HOST + " MAX_ACTIVE:" + MAX_ACTIVE
						+ " MAX_IDLE:" + MAX_IDLE + " MIN_IDLE:" + MIN_IDLE
						+ " MAX_WAIT:" + MAX_WAIT);
				
			} catch (Exception e) {
				logger.error("Create JedisPool error : ", e);
			}
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return Jedis
	 */
	private synchronized static Jedis getJedis() {
		Jedis jedis = null;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
			} else {
				poolInit();
				if (jedisPool != null) {
					jedis = jedisPool.getResource();
				}
			}
		} catch (Exception e) {
			logger.error("Get jedis error : ", e);
			returnResource(jedis);
		}
		
		return jedis;
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null && jedisPool != null) {
			jedis.close();
		}
	}

	/**
	 * 设置 String
	 * 
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
		Jedis jedis = null;
		try {
			if (value != null) {
				jedis = getJedis();
				jedis.set(key, value);
			}
		} catch (Exception e) {
			logger.error("Set key error : ", e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 设置 过期时间
	 * 
	 * @param key
	 * @param seconds 以秒为单位
	 * @param value
	 */
	public static void setString(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			if (value != null) {
				jedis = getJedis();
				jedis.setex(key, seconds, value);
			}
		} catch (Exception e) {
			logger.error("Set keyex error : ", e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 获取String值
	 */
	public static String getString(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			if (jedis != null) {
				return jedis.get(key);
			}
		} catch (Exception e) {
			logger.error("Get key error : ", e);
		} finally {
			returnResource(jedis);
		}
		return null;
	}
	
	public static void main(String[] args) {
		 setString("name", "redis");
		 System.out.println(getString("name"));
	}

}