package com.ginkgocap.ywxt.video.config;

import com.ginkgocap.ywxt.video.interceptor.AccessLogInterceptor;
import com.ginkgocap.ywxt.video.interceptor.SwaggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by gintong on 2017/6/8.
 */
@Configuration
@Import(InterceptorConfiguration.class)
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private AccessLogInterceptor accessLogInterceptor;

    @Autowired
    private SwaggerInterceptor swaggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //api 文档拦截器
        registry.addInterceptor(swaggerInterceptor)
                .addPathPatterns("/swagger-ui.html*");

        //访问日志拦截器
        registry.addInterceptor(accessLogInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui.html*", "/webjars/**","/swagger-resources","/v2/api-docs/**");

        super.addInterceptors(registry);
    }

    /**
     * 资源处理器
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
