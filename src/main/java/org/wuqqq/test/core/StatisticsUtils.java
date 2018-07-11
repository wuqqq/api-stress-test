
package org.wuqqq.test.core;

import java.util.List;

/**
 * @author wuqi 2017/7/21 0021.
 */
public class StatisticsUtils {
    public static long getTotal(List<Long> timeList) {
        long total = 0L;
        for (Long time : timeList) {
            total += time;
        }
        return total;
    }

    public static float toMs(long nm) {
        return nm / 1_000_000.0F;
    }

    public static float getAverage(long total, int size) {
        return (float) total / (float) size;
    }

    public static float toMs(float nm) {
        return nm / 1_000_000.0F;
    }
}
