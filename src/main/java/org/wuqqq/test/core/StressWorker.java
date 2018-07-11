
package org.wuqqq.test.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuqi 2017/7/14 0014.
 */
public class StressWorker implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(StressWorker.class);

    private CyclicBarrier beginBarrier;
    private CountDownLatch endLatch;
    private AtomicInteger failCounter;

    private StressTask task;
    private int perThreadRequests;
    private List<Long> timeList;

    public StressWorker(StressContext stressContext) {
        this.beginBarrier = stressContext.getBeginBarrier();
        this.endLatch = stressContext.getEndLatch();
        this.failCounter = stressContext.getFailCounter();
        this.task = stressContext.getTask();
        this.perThreadRequests = stressContext.getPerThreadRequests();
        timeList = new ArrayList<>(perThreadRequests);
    }

    @Override
    public void run() {
        try {
            beginBarrier.await();
            doRun();
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        } finally {
            endLatch.countDown();
        }
    }

    public void doRun() {
        for (int i = 0; i < perThreadRequests; i++) {
            long start = System.nanoTime();
            try {
                task.doTask();
            } catch (Exception e) {
                failCounter.incrementAndGet();
            } finally {
                long end = System.nanoTime();
                long frame = end - start;
                timeList.add(frame);
            }
        }
    }

    public List<Long> getTimeList() {
        return timeList;
    }
}
