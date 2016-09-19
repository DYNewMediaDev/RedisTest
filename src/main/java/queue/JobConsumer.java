package queue;

import org.springframework.data.redis.core.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.Threads;
import validate.AssertUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * redis rpop 实现消费的消息出队
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 下午2:38:10
 */
public class JobConsumer {

	private String jobKey; // 任务的名字
	private volatile boolean isRunning; // 决定任务是否仍在执行
	private JobListener jobListener; // 任务监听回调
	private ExecutorService executorService; // 任务调度

	private RedisTemplate redisTemplate; // redis connection

	public JobConsumer(String jobKey) {
		this.jobKey = jobKey;
		this.isRunning = true;
	}

	/**
	 * 开始执行
	 */
	public void startConsumer() {

		AssertUtil.assertNotNull(redisTemplate, "redis template is required");

		if (jobListener == null) {
			logger.info("no job listener init, and set it default");
			jobListener = new JobListener() {

				@Override
				public void onStart() {
					logger.info("I start");
				}

				@Override
				public void onMessage(String message) {
					logger.info("I receive message {}", message);
				}

				@Override
				public void onDestory() {
					logger.info("I stop");
				}
			};
		}// --> if null set it do nothing

		try {
			executorService = Executors.newSingleThreadExecutor(Threads
					.buildJobFactory("Job-Consumer-" + jobKey + "-%d"));
			executorService.execute(new Runnable() {

				@Override
				public void run() {
					while (isRunning) {
						try {
							String value = dqueue();
							if (value != null) {
								logger.info("queue:{} pop {}", jobKey, value);
								// callback on message
								jobListener.onMessage(value);
								// wait a while now
								TimeUnit.MILLISECONDS.sleep(300);
							} else {
								// wait a while now
								TimeUnit.MILLISECONDS.sleep(100);
							}// --> end if-else
						} catch (InterruptedException e) {
							// ignore
							// throw new RuntimeException("dqueue error");
							e.printStackTrace();
						}// --> end try-catch
					}// --> end while
						// TODO stop will callback many times
					stop(); // if break while method, stop consumer
				}// --> end thread run
			});// --> end execute
			jobListener.onStart(); // callback on start
		} catch (Exception e) {
			stop();
		}// --> end try-catch
	}

	/**
	 * 外部停止消费者途径
	 */
	public void stopConsumer() {
		// set stop flag
		this.isRunning = false;
	}

	/**
	 * 停止执行
	 */
	private void stop() {
		// set stop flag
		this.isRunning = false;
		// shutdown thread
		if (executorService != null) {
			Threads.normalShutdown(executorService, 5, TimeUnit.SECONDS);
		}
		// callback on stop
		jobListener.onDestory();
	}

	/**
	 * RPOP弹出消息
	 */
	private String dqueue() {
		Object obj = redisTemplate.opsForList().rightPop(jobKey);
        if(obj != null)
            return obj.toString();
        return null;
	}

	/* get/set */
	public void setJobListener(JobListener jobListener) {
		this.jobListener = jobListener;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private static Logger logger = LoggerFactory.getLogger(JobProducer.class);
}
