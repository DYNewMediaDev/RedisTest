/**
 * User: herry
 * Date: 16-8-5 09:50
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import queue.JobConsumer;
import queue.JobListener;
import queue.JobProducer;

import java.util.concurrent.TimeUnit;


/**
 * 测试选举
 *
 * @author zengxm<github.com/JumperYu>
 * @date 2015年11月9日 上午11:13:44
 */
public class TestQueueConsumer {

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-cache-anno.xml");// 加载spring配置文件

        RedisTemplate redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
        String jobKey = "job:test";

        JobConsumer jobConsumer = new JobConsumer(jobKey);
        jobConsumer.setRedisTemplate(redisTemplate);
        jobConsumer.setJobListener(new JobListener(){
            @Override
            public void onStart() {
                System.out.println("start listener");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("receive msg["+message+"]");
            }

            @Override
            public void onDestory() {
                System.out.println("consume destory");
            }
        });
        jobConsumer.startConsumer();

        //TimeUnit.SECONDS.sleep(10);
        //jobConsumer.stopConsumer();
    }



}
