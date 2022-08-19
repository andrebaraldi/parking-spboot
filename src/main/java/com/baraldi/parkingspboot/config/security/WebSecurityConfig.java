package com.baraldi.parkingspboot.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

// Configuration: Classe de configuração do Spring. Com essa anotação ela é um bean
// WebSecurityConfigurerAdapter: Classe que permite configurações customizáveis de autenticação
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	
	// Injetando a nossa implementação de UserDetailsService
	final UserDetailsServiceImpl userDetailsService; 
	
	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	
	// Método da classe WebSecurityConfigurerAdapter 
	// Usamos para termos uma autenticação 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// Implementação de autenticação em memória
		auth.inMemoryAuthentication()
			.withUser("baraldi")
			.password(passwordEncoder().encode("1234"))
			.roles("ADMIN");  // Definimos a rule ADMIN para o user andre
		
		// Implementação de Autenticação com usuário na base de dados
		// Temos que usar o objeto UserDetailsService do Spring Security
		// Nossa Classe UserModel (com o repository, etc.) está indo como nosso UserDetails
	    // auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	     
	}
	

	// Método da classe WebSecurityConfigurerAdapter
	// Usamos para configurar restrições, etc. por aqui
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		/*
		  Configurando o httpSecurity recebido:
		  
		   .httpBasic()              : Vamos usar http basic
	       .and()                    : And é para unir configurações
	       .authorizeHttpRequests()  : Aqui são as requisições
	       .anyRequest().permitAll() : Para qualquer requisição vamos permitir tudo
	       .anyRequest().authenticated() : Necessita de Autenticação
	       .csrf().disable()         : Desabilita o tratamento do Security para a Vulnerabilidade CSRF
	                                   (permitindo assim o POST, PUT e DELETE, usando a configuração básica)
	                                   	       
	       Será aplicada as configurações acima à todas requisições da aplicação.
	    */
		http
		    .httpBasic() 
		    .and()
		    .authorizeHttpRequests()
		    
		    .antMatchers(HttpMethod.GET, "/parking-spboot/**").permitAll()
		    .antMatchers(HttpMethod.POST, "/parking-spboot").hasRole("USER")
		    .antMatchers(HttpMethod.DELETE, "/parking-spboot/**").hasRole("ADMIN")
		    
		    .anyRequest().authenticated()
		    .and()
		    .csrf().disable();
		    ;		
	}


	
	// Bean que indica o passwordEncoder que será utilizado pelo Security em toda
	// nossa aplicação
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();		
	}

}
