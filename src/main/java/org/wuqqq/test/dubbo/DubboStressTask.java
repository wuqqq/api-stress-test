
package org.wuqqq.test.dubbo;

import org.wuqqq.test.core.StressTask;

import java.util.concurrent.Future;

/**
 * @author wuqi 2017/7/21 0021.
 */
public abstract class DubboStressTask implements StressTask {
    public abstract Future<?> doAsyncTask() throws Exception;
}
