package redis.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:Spring-cache-anno.xml")
public class DataRedisTest {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void test() {
		try {

			// 添加一个 key
			ValueOperations<String, Object> value = redisTemplate.opsForValue();
			value.set("lp", "hello word");
			// 获取 这个 key 的值
			System.out.println(value.get("lp"));

			// 添加 一个 hash集合
			HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "lp");
			map.put("age", "26");
			hash.putAll("lpMap", map);

			// 获取 map
			System.out.println(hash.entries("lpMap"));
			// 添加 一个 list 列表
			ListOperations<String, Object> list = redisTemplate.opsForList();
			list.rightPush("lpList", "lp");
			list.rightPush("lpList", "26");
			// 输出 list
			System.out.println(list.range("lpList", 0, 1));

			// 添加 一个 set 集合
			SetOperations<String, Object> set = redisTemplate.opsForSet();
			set.add("lpSet", "lp");
			set.add("lpSet", "26");
			set.add("lpSet", "178cm");
			// 输出 set 集合
			System.out.println(set.members("lpSet"));

			// 添加有序的 set 集合
			ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
			zset.add("lpZset", "lp", 0);
			zset.add("lpZset", "26", 1);
			zset.add("lpZset", "178cm", 2);
			// 输出有序 set 集合
			System.out.println(zset.rangeByScore("lpZset", 0, 2));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
