package br.com.erudio.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.erudio.security.jwt.JwtTokenFilter;
import br.com.erudio.security.jwt.JwtTokenProvider;

@EnableWebSecurity
@Configuration
public class SecutiryConfig {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		Pbkdf2PasswordEncoder Pbkdf2encoder = new Pbkdf2PasswordEncoder("", 8, 185000,
				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		encoders.put("pbkdf2", Pbkdf2encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(Pbkdf2encoder);
		return passwordEncoder();

	}
	@Bean
	AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);
		 return http
	                .httpBasic(basic -> basic.disable())
	                .csrf((csrf -> csrf.disable()))
	                .addFilterBefore(customFilter , UsernamePasswordAuthenticationFilter.class)
	                .sessionManagement(
	            		session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .authorizeHttpRequests(
	                    authorizeHttpRequests -> authorizeHttpRequests
	                        .requestMatchers(
								"/auth/signin",
								"/auth/refresh/**",
	                    		"/swagger-ui/**",
	                    		"/v3/api-docs/**"
	                		).permitAll()
	                        .requestMatchers("/api/**").authenticated()
	                        .requestMatchers("/users").denyAll()
	                )
	                .cors(cors->{})
	                .build();
	}
}
