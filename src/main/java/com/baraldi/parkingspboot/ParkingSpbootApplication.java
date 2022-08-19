package com.baraldi.parkingspboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ParkingSpbootApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ParkingSpbootApplication.class, args);
		
		// Definindo uma senha automoatica para salvar no BD
		// O correto é criar um tratamento para a primeira vez que o usuário
		// acessar
		System.out.println(new BCryptPasswordEncoder().encode("senha123"));
		
		// Testando a senha
		/*
		 * String rawPassword = "senha123";
		 * 
		 * BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); String
		 * encodedPassword = passwordEncoder.encode(rawPassword); // Criptografa a senha
		 * 
		 * System.out.println("encodedPassword (senha 123):");
		 * System.out.println(encodedPassword);
		 * 
		 * boolean matched = passwordEncoder.matches("java2020", encodedPassword);
		 * 
		 * System.out.println("matched?"); System.out.println(matched);
		 */
	}

}
