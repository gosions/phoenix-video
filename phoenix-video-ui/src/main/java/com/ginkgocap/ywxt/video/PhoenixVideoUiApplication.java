package com.ginkgocap.ywxt.video;

import com.ginkgocap.ywxt.video.config.ApplicationConfiguration;
import com.ginkgocap.ywxt.video.config.MyWebAppConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableAutoConfiguration(
		exclude={HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class})
@Configuration
@Import({ApplicationConfiguration.class, MyWebAppConfigurer.class})
@ImportResource("classpath:applicationContext.xml")
@EnableSwagger2
@ComponentScan(basePackages = "com.ginkgocap.ywxt.video")
public class PhoenixVideoUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoenixVideoUiApplication.class, args);
	}
}
