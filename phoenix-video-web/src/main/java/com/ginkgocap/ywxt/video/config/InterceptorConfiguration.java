package com.ginkgocap.ywxt.video.config;

import com.ginkgocap.ywxt.video.interceptor.AccessLogInterceptor;
import com.ginkgocap.ywxt.video.interceptor.SwaggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gintong on 2017/6/9.
 */
@Configuration
public class InterceptorConfiguration {

    @Bean
    public AccessLogInterceptor accessLogInterceptor() {
        return new AccessLogInterceptor();
    }

    @Bean
    public SwaggerInterceptor swaggerInterceptor() {
        return new SwaggerInterceptor();
    }

}
