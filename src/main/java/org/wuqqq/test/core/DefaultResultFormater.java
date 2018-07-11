
package org.wuqqq.test.core;

import java.io.Writer;
import java.util.List;

/**
 * @author wuqi 2017/7/21 0021.
 */
public class DefaultResultFormater implements StressResultFormater {

    @Override
    public void format(StressResult stressResult, Writer writer) {
        long testTakenTime = stressResult.getTestTakenTime();
        int totalRequests = stressResult.getTotalRequests();
        int concurrencyLevel = stressResult.getConcurrencyLevel();
        float takes = StatisticsUtils.toMs(testTakenTime);
        List<Long> allTimes = stressResult.getAllTimes();
        long totalTime = StatisticsUtils.getTotal(allTimes);
        float qps = 1.0E9F * (float) concurrencyLevel * ((float) totalRequests / (float) totalTime);
        float averageTime = StatisticsUtils.getAverage(totalTime, totalRequests);
        float onThreadAverageTime = averageTime / (float) concurrencyLevel;
        int count_50 = totalRequests / 2;
        int count_66 = totalRequests * 66 / 100;
        int count_75 = totalRequests * 75 / 100;
        int count_80 = totalRequests * 80 / 100;
        int count_90 = totalRequests * 90 / 100;
        int count_95 = totalRequests * 95 / 100;
        int count_98 = totalRequests * 98 / 100;
        int count_99 = totalRequests * 99 / 100;
        long longestRequest = allTimes.get(allTimes.size() - 1);
        long shortestRequest = allTimes.get(0);
        StringBuilder report = new StringBuilder();
        report.append("并发数(Concurrency level):\t").append(concurrencyLevel);
        report.append("\r\n 测试耗时(Time taken for tests):\t").append(takes).append(" ms");
        report.append("\r\n 总请求次数(Total requests):\t").append(totalRequests);
        report.append("\r\n 失败次数(Failed requests):\t").append(stressResult.getFailedRequests());
        report.append("\r\n QPS(Requests per second):\t").append(qps);
        report.append("\r\n 平均耗时(Time per request):\t").append(StatisticsUtils.toMs(averageTime)).append(" ms");
        report.append("\r\n 平均耗时，忽略并发影响(Time per request, ignore concurrency requests):\t").append(StatisticsUtils.toMs(onThreadAverageTime)).append(" ms");
        report.append("\r\n 最短的耗时(Shortest request):\t").append(StatisticsUtils.toMs(shortestRequest)).append(" ms");
        report.append("\r\n 请求的百分比耗时 (ms):");
        report.append("\r\n  50%\t").append(StatisticsUtils.toMs(allTimes.get(count_50))).append("--50%的耗时");
        report.append("\r\n  66%\t").append(StatisticsUtils.toMs(allTimes.get(count_66)));
        report.append("\r\n  75%\t").append(StatisticsUtils.toMs(allTimes.get(count_75)));
        report.append("\r\n  80%\t").append(StatisticsUtils.toMs(allTimes.get(count_80)));
        report.append("\r\n  90%\t").append(StatisticsUtils.toMs(allTimes.get(count_90)));
        report.append("\r\n  95%\t").append(StatisticsUtils.toMs(allTimes.get(count_95)));
        report.append("\r\n  98%\t").append(StatisticsUtils.toMs(allTimes.get(count_98)));
        report.append("\r\n  99%\t").append(StatisticsUtils.toMs(allTimes.get(count_99)));
        report.append("\r\n 100%\t").append(StatisticsUtils.toMs(longestRequest)).append(" (longest request)").append("--最长的耗时");
    }
}
