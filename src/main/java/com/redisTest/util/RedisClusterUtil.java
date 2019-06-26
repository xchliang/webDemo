package com.redisTest.util;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import com.liang.util.PropertiesUtil;

/**
 * redis集群连接
 * @author xcl
 *
 */
public class RedisClusterUtil {
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
	private static Set<HostAndPort> nodes = null;
	private static JedisPoolConfig config = null;
	private static JedisCluster cluster = null;

	static {
		Properties prop = PropertiesUtil.getPropertiesFile("redisCluster.properties");
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
		nodes = new HashSet<HostAndPort>();
		str = prop.getProperty("NODES");
		if (str != null) {
			String[] nodeArr = str.split(",");
			for (int i = 0; i < nodeArr.length; i++) {
				String[] node = nodeArr[i].split(":");
				nodes.add(new HostAndPort(node[0], Integer.parseInt(node[1])));
			}
		}
		
		// 建立连接池配置参数
        config = new JedisPoolConfig();
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
	}
 
    /**
     * 获取一个JedisCluster 对象
     *
     * @return
     */
    public static JedisCluster getCluster() {
    	if(cluster==null){
    		cluster = new JedisCluster(nodes, TIMEOUT, TIMEOUT, 3, PASSWORD, config);
    	}
	    return cluster;
    }
 
    /**
	 * 设置 String
	 * 
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
		JedisCluster cluster = null;
		try {
			if (value != null) {
				cluster = getCluster();
				cluster.set(key, value);
			}
		} catch (Exception e) {
			logger.error("Set key error : ", e);
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
		JedisCluster cluster = null;
		try {
			if (value != null) {
				cluster = getCluster();
				cluster.setex(key, seconds, value);
			}
		} catch (Exception e) {
			logger.error("Set keyex error : ", e);
		}
	}

	/**
	 * 获取String值
	 */
	public static String getString(String key) {
		JedisCluster cluster = null;
		try {
			cluster = getCluster();
			if (cluster != null) {
				return cluster.get(key);
			}
		} catch (Exception e) {
			logger.error("Get key error : ", e);
		}
		return null;
	}
 
}