package test;
import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RCountDownLatch;
import org.redisson.core.RLock;
import org.redisson.core.RReadWriteLock;
import org.redisson.core.RSemaphore;
import org.redisson.core.RedissonMultiLock;
import org.redisson.core.RedissonRedLock;

public class RedissonTest {

	static RedissonClient redisson(String masterAddress) throws IOException {
		Config config = new Config();
		config.useMasterSlaveServers().setMasterAddress(masterAddress);
		// config.useClusterServers().addNodeAddress("192.168.10.131:6379");
		return Redisson.create(config);
	}

	static RedissonClient redisson() throws IOException {
		return redisson("192.168.10.131:6379");
	}

	static void lock() throws Exception {
		// 8.1锁（Lock）
		// 继承自java.util.concurrent.locks.Lock接口，可重入、支持TTL（Time To Live）

		RedissonClient redisson = redisson();
		RLock lock = redisson.getLock("anyLock");
		lock.lock();

		// 锁定10秒自动解锁
		lock.lock(10, TimeUnit.SECONDS);

		// Wait for 100 seconds and automatically unlock it after 10 seconds
		boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
		System.out.println("res:" + res);
		lock.unlock();

		// Redisson还提供了一个异步的方法锁对象:
		lock = redisson.getLock("anyLock");
		lock.lockAsync();
		lock.lockAsync(10, TimeUnit.SECONDS);
		Future<Boolean> resF = lock.tryLockAsync(100, 10, TimeUnit.SECONDS);
	}

	static void fairLock() throws Exception {
		// 8.2公平锁（Fair Lock）
		// 继承自java.util.concurrent.locks.Lock接口，可重入、支持TTL（Time To Live）
		// 特点：加锁前检查是否有排队等待的线程，优先排队等待的线程，先来先得
		RedissonClient redisson = redisson();

		RLock fairLock = redisson.getFairLock("anyLock");
		fairLock.lock();

		fairLock.lock(10, TimeUnit.SECONDS);

		// 等待100秒 ，加锁后10秒释放
		boolean res = fairLock.tryLock(100, 10, TimeUnit.SECONDS);
		System.out.println("res:" + res);
		fairLock.unlock();
	}

	static void multiLock() throws Exception {

		// 8.3多种锁（MultiLock）
		// RedissonMultiLock对象组多个RLock对象和处理一个锁。RLock对象可能属于不同的Redisson实例。
		RedissonClient redissonInstance1 = redisson();
		RedissonClient redissonInstance2 = redisson();
		RedissonClient redissonInstance3 = redisson();

		RLock lock1 = redissonInstance1.getLock("lock1");
		RLock lock2 = redissonInstance2.getLock("lock2");
		RLock lock3 = redissonInstance3.getLock("lock3");

		RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
		// locks: lock1 lock2 lock3
		lock.lock();

		lock.unlock();

	}

	static void redLock() throws Exception {
		// 8.4. RedLock
		// RedissonRedLock object implements Redlock locking algorithm.
		// It groups multiple RLock objects and handles them as one lock.
		// Each RLock object may belong to different Redisson instances.
		RedissonClient redissonInstance1 = redisson();
		RedissonClient redissonInstance2 = redisson();
		RedissonClient redissonInstance3 = redisson();

		RLock lock1 = redissonInstance1.getLock("lock1");
		RLock lock2 = redissonInstance2.getLock("lock2");
		RLock lock3 = redissonInstance3.getLock("lock3");

		RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);
		// locks: lock1 lock2 lock3
		lock.lock();

		lock.unlock();
	}

	static void readWriteLock() throws Exception {

		// 8.5. (读写锁)ReadWriteLock
		RedissonClient redisson = redisson();

		RReadWriteLock rwlock = redisson.getReadWriteLock("anyRWLock");
		// Most familiar locking method
		rwlock.readLock().lock();
		// or
		rwlock.writeLock().lock();

		// Lock time-to-live support
		// releases lock automatically after 10 seconds
		// if unlock method not invoked
		rwlock.readLock().lock(10, TimeUnit.SECONDS);
		// or
		rwlock.writeLock().lock(10, TimeUnit.SECONDS);

		boolean res;
		// Wait for 100 seconds and automatically unlock it after 10 seconds
		res = rwlock.readLock().tryLock(100, 10, TimeUnit.SECONDS);
		// or
		System.out.println("res1:" + res);
		res = rwlock.writeLock().tryLock(100, 10, TimeUnit.SECONDS);
		System.out.println("res2:" + res);
		rwlock.readLock().unlock();
		rwlock.writeLock().unlock();
	}

	static void semaphore() throws Exception {
		// 8.6. Semaphore
		// Redisson distributed Semaphore object for Java similar to
		// java.util.concurrent.Semaphore object.

		RedissonClient redisson = redisson();
		RSemaphore semaphore = redisson.getSemaphore("semaphore");
		semaphore.acquire();
		// or
		semaphore.acquireAsync();
		semaphore.acquire(23);
		semaphore.tryAcquire();
		// or
		semaphore.tryAcquireAsync();
		semaphore.tryAcquire(23, TimeUnit.SECONDS);
		// or
		semaphore.tryAcquireAsync(23, TimeUnit.SECONDS);
		semaphore.release(10);
		semaphore.release();
		// or
		semaphore.releaseAsync();
	}

	static void countDownLatch() throws Exception {
		// 8.7. CountDownLatch
		// Redisson distributed CountDownLatch object for Java has structure
		// similar to java.util.concurrent.CountDownLatch object.
		RedissonClient redisson = redisson();

		RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
		latch.trySetCount(1);
		latch.await();

		// in other thread or other JVM
		RCountDownLatch latch1 = redisson.getCountDownLatch("anyCountDownLatch");
		latch1.countDown();
	}

	public static void main(String[] args) {

		try {
			// 8.3多种锁（MultiLock）
			// RedissonMultiLock对象组多个RLock对象和处理一个锁。RLock对象可能属于不同的Redisson实例。
			RedissonClient redissonInstance1 = redisson("127.0.0.1:63791");
			RedissonClient redissonInstance2 = redisson("127.0.0.1:63792");

			RLock lock1 = redissonInstance1.getLock("lock1");
			RLock lock2 = redissonInstance2.getLock("lock2");

			RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2);
			// locks: lock1 lock2
			lock.lock();

			System.out.println("finsh!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
