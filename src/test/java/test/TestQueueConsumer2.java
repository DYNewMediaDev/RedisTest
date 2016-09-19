package test;

/**
 * User: herry
 * Date: 16-8-5 09:50
 */

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试选举
 *
 * @author zengxm<github.com/JumperYu>
 * @date 2015年11月9日 上午11:13:44
 */
public class TestQueueConsumer2 {

	public static void main(String[] args) {

		new ClassPathXmlApplicationContext("spring-cache-anno.xml");// 加载spring配置文件

		while (true) {

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
