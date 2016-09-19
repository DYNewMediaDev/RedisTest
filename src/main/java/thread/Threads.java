package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工具类
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 上午11:12:41
 */
public class Threads {

	/**
	 * 创建ThreadFactory，使得创建的线程有自己的名字而不是默认的"pool-x-thread-y"，
	 * 在用threaddump查看线程时特别有用。 格式如"mythread-%d"，使用了Guava的工具类
	 */
	public static ThreadFactory buildJobFactory(final String nameFormat) {
		final ThreadFactory threadFactory = Executors.defaultThreadFactory();
		return new ThreadFactory() {
			// 记录线程编号
			AtomicInteger count = new AtomicInteger(0);

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = threadFactory.newThread(r);
				thread.setName(String.format(nameFormat, count.getAndIncrement()));
				return thread;
			}
		};
	}

	/**
	 * 直接调用shutdownNow的方法, 有timeout控制.取消在workQueue中Pending的任务,并中断所有阻塞函数.
	 */
	public static void normalShutdown(ExecutorService pool, int timeout, TimeUnit timeUnit) {
		try {
			pool.shutdownNow();
			if (!pool.awaitTermination(timeout, timeUnit)) {
				System.err.println("Pool did not terminated");
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}

}
