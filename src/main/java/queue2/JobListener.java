package queue2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 任务接听回调接口定义
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 下午2:07:11
 */
public class JobListener implements MessageListener {

	private RedisTemplate redisTemplate;

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private ExecutorService executorService = Executors.newSingleThreadExecutor(); // 任务调度

	private static final String jobKey = "job:test";

	private static Logger logger = LoggerFactory.getLogger(JobListener.class);

	@Override
	public void onMessage(Message message, byte[] pattern) {

		System.out.println("onMessage {} " + jobKey);

		while (true) {

			Object obj = redisTemplate.opsForList().rightPop(jobKey);

			if (obj != null) {
				System.out.println("queue:" + jobKey + " pop " + obj);
			} else
				break;

		}
	}

}
