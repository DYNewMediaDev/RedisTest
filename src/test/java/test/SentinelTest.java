package test;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class SentinelTest {

	public static void main33(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-cache-anno.xml");// 加载spring配置文件

		RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) context.getBean("redisTemplate");
		redisTemplate.opsForValue().set("dayang", "dayangValue");

		System.out.println("finsh!");
	}

	public static void main(String[] args) {
		Set<String> sentinels = new HashSet<String>(1);
		sentinels.add("192.168.1.115:26379");// 集群中所有sentinels的地址
		// sentinels.add("192.168.190.128:27001");
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(10);
		config.setMaxIdle(10);
		JedisSentinelPool jedisSentinelPool = null;
		
		Jedis jedis = null;
		
		try {
			// setinel客户端提供了master自动发现功能
			jedisSentinelPool = new  JedisSentinelPool("master", sentinels);
			jedis = jedisSentinelPool.getResource();

			//
			jedis.set("key", "value");
		} finally {
			if (jedisSentinelPool != null) {
				jedisSentinelPool.returnResource(jedis);
				jedisSentinelPool.close();
			}

		}

	}
}
