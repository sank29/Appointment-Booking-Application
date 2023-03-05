package com.sanket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
@Configuration
public class SpringdocConfig {
	
	@Bean
	public OpenAPI baseOpenAPI() {
		
		return new OpenAPI().info(new Info().title("Spring Doc").version("3.0.0").description("Spring doc"));
		
	}

}
