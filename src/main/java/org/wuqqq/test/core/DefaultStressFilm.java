
package org.wuqqq.test.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuqi 2017/7/7 0007.
 */
public class DefaultStressFilm implements StressFilm {

    private static final Logger logger = LoggerFactory.getLogger(DefaultStressFilm.class);

    /**
     * 任务
     */
    private StressTask task;

    /**
     * 并发数
     */
    private int concurrencyLevel;

    /**
     * 请求数
     */
    private int totalRequests;

    /**
     * 暖场次数
     */
    private int warmUpTimes;

    public static final int DEFAULT_WARM_UP_TIMES = 1600;

    private final StressTask emptyTask = new StressTask() {
        @Override
        public Object doTask() throws Exception {
            return null;
        }
    };

    static {
        warmSelf();
    }

    public DefaultStressFilm(int concurrencyLevel, int totalRequests, StressTask task) {
        this(concurrencyLevel, totalRequests, task, DEFAULT_WARM_UP_TIMES);
    }

    public DefaultStressFilm(int concurrencyLevel, int totalRequests, StressTask task, int warmUpTimes) {
        this.task = task;
        this.concurrencyLevel = concurrencyLevel;
        this.totalRequests = totalRequests;
        this.warmUpTimes = warmUpTimes;
    }

    public static void warmSelf() {
        DefaultStressFilm stressFilm = new DefaultStressFilm(10, 100, null, 0);
        stressFilm.show();
    }

    public void warmUp() {
        for (int i = 0; i < warmUpTimes; i++) {
            try {
                task.doTask();
            } catch (Exception e) {
                logger.error("task execute error", e);
            }
        }
    }

    @Override
    public StressResult show() {
        if (task == null)
            task = emptyTask;
        warmUp();
        int perThreadRequests = totalRequests / concurrencyLevel;
        CyclicBarrier beginBarrier = new CyclicBarrier(concurrencyLevel, new Runnable() {
            @Override
            public void run() {
                logger.info("the film shows now......");
            }
        });
        CountDownLatch endLatch = new CountDownLatch(concurrencyLevel);
        AtomicInteger failCounter = new AtomicInteger();
        StressContext stressContext = StressContext.builder().task(task).beginBarrier(beginBarrier).endLatch(endLatch).failCounter(failCounter)
                .perThreadRequests(perThreadRequests).build();
        StressThreadPoolFactory factory = new StressThreadPoolFactory(concurrencyLevel);
        ExecutorService executorService = factory.getThreadPool();
        List<StressWorker> workers = new ArrayList<>(concurrencyLevel);
        for (int i = 0; i < concurrencyLevel; i++) {
            workers.add(new StressWorker(stressContext));
        }
        for (StressWorker worker : workers) {
            executorService.submit(worker);
        }
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        executorService.shutdownNow();
        StressResult result = new StressResult();
        result.setConcurrencyLevel(concurrencyLevel);
        result.setFailedRequests(failCounter.get());
        int realTotalRequests = concurrencyLevel * perThreadRequests;
        result.setTotalRequests(realTotalRequests);
        List<Long> allTimes = new ArrayList<>(realTotalRequests);
        List<Long> threadTimes = new ArrayList<>(concurrencyLevel);
        SortResult sortResult = sortTimes(workers, allTimes, threadTimes);
        result.setAllTimes(sortResult.allTimes);
        result.setTestTakenTime(sortResult.threadTimes.get(sortResult.threadTimes.size() - 1));
        result.setWorkers(workers);
        return result;
    }

    private SortResult sortTimes(List<StressWorker> workers, List<Long> allTimes, List<Long> threadTimes) {
        for (StressWorker worker : workers) {
            threadTimes.add(StatisticsUtils.getTotal(worker.getTimeList()));
            allTimes.addAll(worker.getTimeList());
        }
        Collections.sort(threadTimes);
        Collections.sort(allTimes);
        SortResult sortResult = new SortResult();
        sortResult.allTimes = allTimes;
        sortResult.threadTimes = threadTimes;
        return sortResult;
    }

    class SortResult{
        List<Long> allTimes;
        List<Long> threadTimes;
    }
}
