package test;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import entity.User;
import queue2.JobProducer;

/**
 * User: herry Date: 16-8-5 10:28
 */
public class TestQueueProducer2 {

	private static final String TRANS_CODE_QUEUE_KEY = "trans_code_queue";
	private static final String TRANS_CODE_CHANNEL = "trans_code";

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-cache-anno.xml");// 加载spring配置文件

		RedisTemplate<String, Map<String, Object>> redisTemplate = (RedisTemplate<String, Map<String, Object>>) context
				.getBean("redisTemplate");

		Map<String, Object> value = null;

		value = new HashMap<String, Object>();
		value.put("userName", "user1");
		value.put("password", "user");

		redisTemplate.opsForList().leftPush(TRANS_CODE_QUEUE_KEY, value);

		new Thread(new SendJob(redisTemplate, value)).start();

		value = new HashMap<String, Object>();
		value.put("userName", "user2");
		value.put("password", "user");
		new Thread(new SendJob(redisTemplate, value)).start();

		value = new HashMap<String, Object>();
		value.put("userName", "user3");
		value.put("password", "user");
		new Thread(new SendJob(redisTemplate, value)).start();

		System.out.println("send finish!");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(0);
	}

	public static class SendJob implements Runnable {

		RedisTemplate redisTemplate;
		Map<String, Object> value;

		public SendJob(RedisTemplate redisTemplate, Map<String, Object> value) {
			this.redisTemplate = redisTemplate;
			this.value = value;
		}

		public void run() {
			redisTemplate.convertAndSend(TRANS_CODE_CHANNEL, value);
		}
	}
}
