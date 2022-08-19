package com.baraldi.parkingspboot.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baraldi.parkingspboot.models.UserModel;


// Extendemos nossa interface para a jpaRepository, que será o respositório de nossa entidade
@Repository   // Não é necessário essa anotação já que estendemos do JpaRepository. 
              // Mas colocamos para fins didáticos
public interface UserRepository extends JpaRepository<UserModel, UUID>{
	
	// Método para busca do usuário por nome
	Optional<UserModel> findByUsername(String username);

}
