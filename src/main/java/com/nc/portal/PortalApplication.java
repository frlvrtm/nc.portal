package com.nc.portal;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PortalApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PortalApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

	@Bean
	public WebServerFactoryCustomizer customizer() {
		return container -> {
			if (container instanceof TomcatServletWebServerFactory) {
				TomcatServletWebServerFactory tomcat = (TomcatServletWebServerFactory) container;
				tomcat.addContextCustomizers(context -> context.setCookieProcessor(new LegacyCookieProcessor()));
			}
		};
	}

}