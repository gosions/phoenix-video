package com.ginkgocap.ywxt.video.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by gintong on 2017/6/5.
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(paths())
                .build();
    }

    private Predicate<String> paths() {
        return or(
                regex("/v1.*")
        );
    }
}
