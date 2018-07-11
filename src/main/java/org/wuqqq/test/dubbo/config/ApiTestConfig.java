
package org.wuqqq.test.dubbo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuqi 2017/7/7 0007.
 */
@Configuration
@ComponentScan("org.wuqqq.test")
public class ApiTestConfig {

    private static final String DUBBO_ANNOTATION_PACKAGE = "org.wuqqq.test";

    @Bean
    public static AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(DUBBO_ANNOTATION_PACKAGE);
        return annotationBean;
    }
}
