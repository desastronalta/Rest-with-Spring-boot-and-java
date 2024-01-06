package br.com.erudio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * diz ao springboot que quando estiver subindo a aplicação ele deve ler esta classe pois nela
 * tem configs sobre o comportamento da api.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

	/*
	 * -
	 */
	@Value("${cors.originPatterns: default}")
	private String corsOriginPatterns = "";
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		var allowedOrigins = corsOriginPatterns.split(",");
		registry.addMapping("/**")
		.allowedMethods("*")
		.allowedOrigins(allowedOrigins)
		.allowCredentials(true);
		//.allowedMethods("GET" , "POST" , "PUT") para adicionar métodos especificos 
	}
	
//	@Override
//	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.add(new YamlJackson2HttpMesageConverter());
//	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		configurer.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json",MediaType.APPLICATION_JSON)
		.mediaType("xml", MediaType.APPLICATION_XML)
		.mediaType("x-yml", MEDIA_TYPE_APPLICATION_YML);
		
	}
	
}
