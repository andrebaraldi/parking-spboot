package com.baraldi.parkingspboot.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.baraldi.parkingspboot.models.ParkingSpotModel;
import java.util.UUID;


// Extendemos nossa interface para a jpaRepository, que será o respositório de nossa entidade
@Repository   // Não é necessário essa anotação já que estendemos do JpaRepository. 
              // Mas colocamos para fins didáticos
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID>{

	// ---------------------------------------------
	// Métodos automatizados JPA
	// ---------------------------------------------
	// Métodos para validações para evitar duplicidade
	boolean existsByLicensePlateCar(String licensePlateCar);
	boolean existsByParkingSpotNumber(String parkingSpotNumber);
	boolean existsByApartmentAndBlock(String apartment, String block);	
}
