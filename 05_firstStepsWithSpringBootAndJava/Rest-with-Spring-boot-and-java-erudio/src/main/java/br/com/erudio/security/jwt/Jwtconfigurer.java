package br.com.erudio.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class Jwtconfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	
	public Jwtconfigurer(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}


	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
		http.addFilterBefore(customFilter , UsernamePasswordAuthenticationFilter.class);
	}
	
	 
	
}
