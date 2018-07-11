
package org.wuqqq.test.core;

import java.util.List;

/**
 * @author wuqi 2017/7/21 0021.
 */
public class StressResult {
    /**
     * 并发数
     */
    private int concurrencyLevel;
    /**
     * 总请求数
     */
    private int totalRequests;

    /**
     * 测试耗时
     */
    private long testTakenTime;

    /**
     * 失败的请求数
     */
    private int failedRequests;

    /**
     * 所有请求耗时集合
     */
    private List<Long> allTimes;

    /**
     * 任务集合
     */
    private List<StressWorker> workers;

    public int getConcurrencyLevel() {
        return concurrencyLevel;
    }

    public void setConcurrencyLevel(int concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }

    public long getTestTakenTime() {
        return testTakenTime;
    }

    public void setTestTakenTime(long testTakenTime) {
        this.testTakenTime = testTakenTime;
    }

    public int getFailedRequests() {
        return failedRequests;
    }

    public void setFailedRequests(int failedRequests) {
        this.failedRequests = failedRequests;
    }

    public List<Long> getAllTimes() {
        return allTimes;
    }

    public void setAllTimes(List<Long> allTimes) {
        this.allTimes = allTimes;
    }

    public List<StressWorker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<StressWorker> workers) {
        this.workers = workers;
    }
}
