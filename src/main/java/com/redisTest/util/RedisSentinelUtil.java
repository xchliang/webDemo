package com.redisTest.util;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import com.liang.util.PropertiesUtil;

/**
 * redis哨兵模式连接
 * @author xcl
 *
 */
public class RedisSentinelUtil {
     
    private static JedisSentinelPool pool = null;
 
	protected static Logger logger = Logger.getLogger(RedisUtil.class);
	// 哨兵监控mater名称
	private static String MASTER_NAME = "mymaster";
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
	private static Set<String> sentinels = null;

	static {
		Properties prop = PropertiesUtil.getPropertiesFile("redisSentinel.properties");
		PASSWORD = prop.getProperty("PASSWORD");
		MASTER_NAME = prop.getProperty("MASTER_NAME", MASTER_NAME);
		String str = prop.getProperty("MAX_ACTIVE");
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
		sentinels = new HashSet<String>();
		str = prop.getProperty("SENTINELS");
		if (str != null) {
			String[] senArr = str.split(",");
			for (int i = 0; i < senArr.length; i++) {
				sentinels.add(senArr[i]);
			}
		}
	}
 
    /**
     * 创建连接池
     *
     */
    private static void createJedisPool() {
        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(MAX_ACTIVE);
        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWaitMillis(MAX_WAIT);
        // 设置空间连接
        config.setMaxIdle(MAX_IDLE);
        config.setMinIdle(MIN_IDLE);
        // jedis实例是否可用
        config.setTestOnBorrow(TEST_ON_BORROW);
        System.out.println("MASTER_NAME:" + MASTER_NAME + " MAX_ACTIVE:" + MAX_ACTIVE
        		+ " MAX_IDLE:" + MAX_IDLE + " MIN_IDLE:" + MIN_IDLE
        		+ " MAX_WAIT:" + MAX_WAIT+" TIMEOUT:"+TIMEOUT);
        // 创建连接池
        pool = new JedisSentinelPool(MASTER_NAME, sentinels, config, TIMEOUT, PASSWORD);
    }
 
    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (pool == null)
            createJedisPool();
    }
 
    /**
     * 获取一个jedis 对象
     *
     * @return
     */
    public static Jedis getJedis() {
    	Jedis jedis = null;
		try {
			if (pool != null) {
				jedis = pool.getResource();
			} else {
				poolInit();
				if (pool != null) {
					jedis = pool.getResource();
				}
			}
		} catch (Exception e) {
			logger.error("Get jedis error : ", e);
			returnResource(jedis);
		}
		return jedis;
    }
 
    /**
     * 释放一个连接
     *
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
    	if (jedis != null && pool != null) {
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
		 setString("age", 30,"11");
		 System.out.println(getString("age"));
	}
 
}