package com.example.serviceIntegration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@Slf4j
@EnableAutoConfiguration
public class ApplicationStarter extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configureApplication(builder);
	}


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ApplicationStarter.class);
		app.setBannerMode(Banner.Mode.OFF);
	//	ElasticApmAttacher.attach();
		app.run(args);
	//	ElasticApmAttacher.attach();
	//	configureApplication(new SpringApplicationBuilder()).run(args);
	}

	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
	//	ElasticApmAttacher.attach();
		return builder.sources(ApplicationStarter.class);
	}


/*	public static void main(String[] args) {
		RuntimeAttach.attachJavaagentToCurrentJVM();
		SpringApplication.run(ApplicationStarter.class, args);
		SpringApplication app = new SpringApplication(ApplicationStarter.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}*/

}
