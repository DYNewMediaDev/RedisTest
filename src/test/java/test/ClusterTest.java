package test;
import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class ClusterTest {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-cache-anno.xml");// 加载spring配置文件

		RedisTemplate<String, String> redisTemplate = (RedisTemplate<String, String>) context.getBean("redisTemplate");

		for (int i = 0; i < 10000; i++) {
			try {
				String key = "dayang55_" + i;
				String value = "dayangvalue55_" + i;
				redisTemplate.opsForValue().set(key, value);

				System.out.println("key:" + key + ", value:" + value);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(redisTemplate);

	}
}
