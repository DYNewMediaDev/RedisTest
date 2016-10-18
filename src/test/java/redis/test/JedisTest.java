package redis.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;
import service.JedisCacheService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:Spring-cache-anno.xml")
public class JedisTest {

	@Autowired
	private JedisCacheService jedisCacheService;

	@Autowired
	private JedisPool jedisPool;

	@Test
	public void test() {

		try {
			boolean returnRes = jedisCacheService.set("key", "value");

			println(returnRes);

			println("key:" + jedisCacheService.get("key"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private Jedis getJedis() {
		return jedisPool.getResource();
	}

	public static void println(Object message) {
		System.out.println("********:" + message);
	}

	@Test
	public void test1Str() {
		try {
			Jedis jedis = getJedis();

			// 存储数据
			jedis.set("foo", "bar");
			println("存储数据:" + jedis.get("foo"));

			// 覆盖数据
			jedis.set("foo", "foo update");
			println("覆盖数据:" + jedis.get("foo"));

			// 追加数据
			jedis.append("foo", " hello, world");
			println("append:" + jedis.get("foo"));

			// 若key不存在，则存储
			jedis.setnx("foo", "foo not exits");
			println("setnx:" + jedis.get("foo"));

			// 设置key的有效期，并存储数据
			jedis.setex("foo", 2, "foo not exits");
			println("setex:" + jedis.get("foo"));
			Thread.sleep(2000);

			// 是否存在key
			println("setex 2 seconds after:" + jedis.exists("foo"));

			// 获取并更改数据
			println("getSet: old: " + jedis.getSet("foo", "foo modify") + " new: " + jedis.get("foo"));

			// 批量添加
			println("mset: "
					+ jedis.mset("mset1", "mvalue1", "mset2", "mvalue2", "mset3", "mvalue3", "mset4", "mvalue4"));
			println("mget: " + jedis.mget("mset1", "mset2", "mset3", "mset4"));

			// 批量删除key
			println(jedis.del(new String[] { "foo", "foo1", "foo3" }));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void test2List() {

		try {
			Jedis jedis = getJedis();
			String value;

			// 1. 右边入队：
			jedis.rpush("userList", "James");

			// 2. 左边出队：右边出栈(rpop)，即为对堆栈的操作。
			value = jedis.lpop("userList");
			println("2." + value);

			// 3. 返回列表范围：从0开始，到最后一个(-1) [包含]
			jedis.lpush("userList", "user1");
			jedis.lpush("userList", "user2");
			jedis.lpush("userList", "user3");
			List<String> userList = jedis.lrange("userList", 0, -1);
			for (String string : userList) {
				println("3." + string);
			}

			// 4. 删除：使用key
			// jedis.del("userList");

			// 5. 设置：位置1处为新值
			jedis.lset("userList", 1, "Nick Xu");
			println("5." + jedis.lindex("userList", 1));

			// 6. 返回长度：
			Long size = jedis.llen("userList");
			println("6.size = " + size);

			// 7. 进行裁剪：包含 ; 对一个列表进行修剪(trim)，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
			value = jedis.ltrim("userList", 0, 2);
			println("7.ltrim = " + value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void test3Set() {
		try {
			Jedis jedis = getJedis();
			Set<String> fruit;

			println("1.向set中添加元素");
			jedis.sadd("fruit", "apple");
			jedis.sadd("fruit", "pear", "watermelon");
			jedis.sadd("fruit", "apple");// 重复添加测试

			println("2. 遍历集合：");
			fruit = jedis.smembers("fruit");
			for (Iterator<String> it = fruit.iterator(); it.hasNext();) {
				println("2." + it.next());
			}

			println("3. 移除元素：remove");
			jedis.srem("fruit", "pear");
			fruit = jedis.smembers("fruit");
			for (Iterator<String> it = fruit.iterator(); it.hasNext();) {
				println("3." + it.next());
			}

			println("4. 返回长度：");
			Long size = jedis.scard("fruit");
			println("size: " + size);

			println("5. 是否包含：");
			Boolean isMember = jedis.sismember("fruit", "pear");
			println("isMember：" + isMember);

			println("6. 集合的操作：包括集合的交运算(sinter)、差集(sdiff)、并集(sunion)");
			// jedis.sadd("fruit", "milk");
			jedis.sadd("food", "bread", "milk");
			fruit = jedis.sunion("fruit", "food");

			for (Iterator<String> it = fruit.iterator(); it.hasNext();) {
				println("6." + it.next());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用sorted set：有序集合在集合的基础上，增加了一个用于排序的参数。 有序集合：根据“第二个参数”进行排序。
	 * 
	 *
	 */
	@Test
	public void test4ZSet() {
		try {
			Jedis jedis = getJedis();

			jedis.zadd("user", 22.1, "James");
			jedis.zadd("user", 24.1, "James");// 再次添加相同时更新权重
			jedis.zadd("user", 25.1, "James1");
			jedis.zadd("user", 26.1, "James2");
			jedis.zadd("user", 27.1, "James3");

			// 获取有序集合的成员数
			Long size = jedis.zcard("user");
			println("zcard: " + size);

			// 计算在有序集合中指定区间分数的成员数
			size = jedis.zcount("user", 25, 26);
			println("zcount:" + size);

			// 3. zset的范围：找到从0到-1的所有元素。
			Set<String> user = jedis.zrange("user", 0, -1);
			for (Iterator<String> it = user.iterator(); it.hasNext();) {
				println("3." + it.next());
			}

			println("sort*******");
			SortingParams sortingParams = new SortingParams();
			List<String> userList = jedis.sort("user", sortingParams.desc());
			for (String str : userList) {
				println(str);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1. 存放数据：使用HashMap
	 * 
	 *
	 */
	@Test
	public void test5Hash() {
		try {
			Jedis jedis = getJedis();

			Map<String, String> capital = new HashMap<String, String>();
			capital.put("shannxi", "xi'an");
			capital.put("shanghai", "wai'tan");

			jedis.hmset("capital", capital);

			// 获取数据：
			List<String> cities = jedis.hmget("capital", "shannxi", "shanghai");
			for (String string : cities) {
				println(string);
			}

			// 返回key为user的键中存放的值的个数
			println("hlen: " + jedis.hlen("capital"));

			// 是否存在key为user的记录
			println("exists: " + jedis.exists("capital"));

			// 返回map对象中的所有key
			println("hkeys: " + jedis.hkeys("capital"));

			// 返回map对象中的所有value
			println("hvals: " + jedis.hvals("capital"));

			// 返回一个map对象，包含所有键值
			println("hgetAll: " + jedis.hgetAll("capital"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
