package com.baraldi.parkingspboot.config.security;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baraldi.parkingspboot.models.UserModel;
import com.baraldi.parkingspboot.repositories.UserRepository;

// Classe para uso do Spring Security 
// UserDetailsService : Faz parte do Core do Spring Security.
//                       Usada para acessar o usuário na base de dados.
@Service
@Transactional         // Usa ao buscar os dados do BD (as Roles do usuário)
public class UserDetailsServiceImpl implements UserDetailsService {

	// Ponto de injeção do Repositório do User
	// @Autowired
	// UserRepository userRepository; 
	
	// Vamos criar o ponto de injeção do Repositório via construtor
	final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}	
	
	
	// Método da interface UserDetailsService
	// Retorna um UserDetails
	// Busca o usuário pelo username. Então temos que garantir que o username seja uma pk na base de dados
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		// Pesquisa o usuário pelo seu username		
		UserModel userModel = userRepository.findByUsername(username)
				              .orElseThrow( () -> new UsernameNotFoundException("User Not Found with username: " +username) )
				              ; 
		
		// Podemos retornar o nosso userModel como UserDetails
		// porque nosso modelo implementa UserDetails:
		// public class UserModel implements UserDetails...
		//return userModel;
		
		return new User(userModel.getUsername(), userModel.getPassword(), true, true, true,true, userModel.getAuthorities());		
	}
	

}
