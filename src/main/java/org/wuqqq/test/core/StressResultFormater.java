
package org.wuqqq.test.core;

import java.io.Writer;

/**
 * @author wuqi 2017/7/21 0021.
 */
public interface StressResultFormater {
    void format(StressResult result, Writer writer);
}
