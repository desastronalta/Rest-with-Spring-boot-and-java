package br.com.erudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	/*
	 * Abaixo temos a criação de um Bean que vai servir para passar algumas informações para o swagger assim sendo utilizados
	 * para passar o contexto geral da nossa API.
	 */
	@Bean
	public OpenAPI customOperAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("RESTfull API with java 19 and Spring Boot 3.1.5")
						.version("v1")
						.description("description about my API")
						.termsOfService("link of terms of services")
						.license(new License().name("Apache 2.0")
								.url("link of license")));
	}
	
}
