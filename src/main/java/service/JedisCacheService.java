package service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

@Service
public class JedisCacheService {

	private final Logger log = Logger.getLogger(JedisCacheService.class);

	@Autowired
	private JedisPool jedisPool;

	public boolean setString(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			if (null != jedis) {

				String setex = jedis.set(key, value);

				return setex.equalsIgnoreCase("OK");
			}
		} catch (JedisException e) {
			log.error("[Redis] 调用 setString 方法失败 key->" + key, e);
		} finally {
			closeResource(jedis);
		}

		return false;
	}

	public boolean set(String key, Object bValue) {

		Jedis jedis = jedisPool.getResource();
		try {
			if (null != jedis) {
				byte[] objArry = SerializationUtils.serialize(bValue);

				String setex = jedis.set(key.getBytes(), objArry);

				return setex.equalsIgnoreCase("OK");
			}
		} catch (JedisException e) {
			log.error("[Redis] 调用 set 方法失败 key->" + key, e);
		} finally {
			closeResource(jedis);
		}

		return false;
	}

	public Object get(String key) {
		byte[] bValue = null;
		Jedis jedis = jedisPool.getResource();
		try {
			if (null != jedis) {
				bValue = jedis.get(key.getBytes());
				if (null == bValue) {
					return null;
				}

				return SerializationUtils.deserialize(bValue);
			}
		} catch (JedisException e) {
			log.error("[Redis] 调用 get 方法失败 key->" + key, e);
		} finally {
			closeResource(jedis);
		}
		return null;
	}

	protected void closeResource(Jedis jedis) {
		jedis.close();
	}

	protected boolean handleJedisException(JedisException jedisException) {
		if (jedisException instanceof JedisConnectionException) {
			log.error("Redis 尝试连接 ", jedisException);
		} else if (jedisException instanceof JedisDataException) {
			if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
				log.error("Redis connection are read-only slave.", jedisException);
			} else {
				return false;
			}
		} else {
			log.error("Jedis 发生异常.", jedisException);
		}
		return true;
	}
}
