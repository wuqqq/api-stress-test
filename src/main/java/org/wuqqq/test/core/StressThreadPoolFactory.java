
package org.wuqqq.test.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池，corePoolSize = maxPoolSize = queue size
 * 
 * @author wuqi 2017/7/14 0014.
 */
public class StressThreadPoolFactory {

    private static final Logger logger = LoggerFactory.getLogger(StressThreadPoolFactory.class);

    private int poolSize;

    public StressThreadPoolFactory(int poolSize) {
        this.poolSize = poolSize;
    }

    public ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(poolSize), new NamedThreadFactory(),
                new AbortPolicyWithReport());
    }

    class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            String name = "stress-thread" + threadNum.getAndIncrement();
            Thread t = new Thread(r, name);
            t.setDaemon(true);
            return t;
        }
    }

    class AbortPolicyWithReport extends ThreadPoolExecutor.AbortPolicy {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            String msg = String.format(
                    "Thread pool is EXHAUSTED!" + " Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d),"
                            + " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)",
                    "stress-thread", e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(), e.getTaskCount(),
                    e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
            logger.warn(msg);
            throw new RejectedExecutionException(msg);
        }
    }
}
