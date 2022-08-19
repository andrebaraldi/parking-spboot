package com.baraldi.parkingspboot.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Nessa versão não vamos herdar da classe WebSecurityConfigurerAdapter
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfigV2 {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		    .httpBasic() 
		    .and()
		    .authorizeHttpRequests()
		    
		    //.antMatchers(HttpMethod.GET, "/parking-spboot/**").permitAll()
		    //.antMatchers(HttpMethod.POST, "/parking-spboot").hasRole("USER")
		    //.antMatchers(HttpMethod.DELETE, "/parking-spboot/**").hasRole("ADMIN")
		    
		    .anyRequest().authenticated()
		    .and()
		    .csrf().disable();
		    ;	
		
		// Agora vamos ter que retornar nossa configuraçao de http, pois isso é um bean e
		// não mais um override da classe WebSecurityConfigurerAdapter
		return http.build();
	}
	
	
	// Bean que indica o passwordEncoder que será utilizado pelo Security em toda
	// nossa aplicação
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();		
	}
	

}
