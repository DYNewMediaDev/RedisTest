import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import queue.JobProducer;

/**
 * User: herry
 * Date: 16-8-5 10:28
 */
public class TestQueueProducer {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-cache-anno.xml");// 加载spring配置文件

        RedisTemplate redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
        String jobKey = "job:test";
        JobProducer jobProducer = new JobProducer(jobKey);

        jobProducer.setRedisTemplate(redisTemplate);
        long i = 0;
        while (true) {
            jobProducer.queue("test-" + (i++));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
