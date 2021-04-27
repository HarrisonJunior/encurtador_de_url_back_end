package com.harrison.spring_security_jpa.config;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	/**Bean para validação de url */
	@Bean
	public UrlValidator getUrlValidator() {
		return new UrlValidator();
	}
	
}
