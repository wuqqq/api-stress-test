
package org.wuqqq.test.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuqi 2017/7/14 0014.
 */
public class StressContext {
    private CyclicBarrier beginBarrier;
    private CountDownLatch endLatch;
    private AtomicInteger failCounter;

    private StressTask task;
    private int perThreadRequests;

    public static StressContextBuilder builder() {
        return new StressContextBuilder();
    }

    public static class StressContextBuilder {

        private StressContext stressContext;

        public StressContextBuilder() {
            stressContext = new StressContext();
        }

        public StressContextBuilder beginBarrier(CyclicBarrier beginBarrier) {
            stressContext.setBeginBarrier(beginBarrier);
            return this;
        }

        public StressContextBuilder endLatch(CountDownLatch endLatch) {
            stressContext.setEndLatch(endLatch);
            return this;
        }

        public StressContextBuilder failCounter(AtomicInteger failCounter) {
            stressContext.setFailCounter(failCounter);
            return this;
        }

        public StressContextBuilder task(StressTask task) {
            stressContext.setTask(task);
            return this;
        }

        public StressContextBuilder perThreadRequests(int perThreadRequests) {
            stressContext.setPerThreadRequests(perThreadRequests);
            return this;
        }

        public StressContext build() {
            return stressContext;
        }
    }

    public CyclicBarrier getBeginBarrier() {
        return beginBarrier;
    }

    public void setBeginBarrier(CyclicBarrier beginBarrier) {
        this.beginBarrier = beginBarrier;
    }

    public CountDownLatch getEndLatch() {
        return endLatch;
    }

    public void setEndLatch(CountDownLatch endLatch) {
        this.endLatch = endLatch;
    }

    public AtomicInteger getFailCounter() {
        return failCounter;
    }

    public void setFailCounter(AtomicInteger failCounter) {
        this.failCounter = failCounter;
    }

    public StressTask getTask() {
        return task;
    }

    public void setTask(StressTask task) {
        this.task = task;
    }

    public int getPerThreadRequests() {
        return perThreadRequests;
    }

    public void setPerThreadRequests(int perThreadRequests) {
        this.perThreadRequests = perThreadRequests;
    }
}
