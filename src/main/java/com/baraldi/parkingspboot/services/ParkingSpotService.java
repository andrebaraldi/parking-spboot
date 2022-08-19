package com.baraldi.parkingspboot.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baraldi.parkingspboot.models.ParkingSpotModel;
import com.baraldi.parkingspboot.models.UserModel;
import com.baraldi.parkingspboot.repositories.ParkingSpotRepository;


@Service  // Indicando que a nossa classe é um Service
          // que atuará entre o CONTROLLER x REPOSITORY
public class ParkingSpotService {

	// Injeção de dependência para o nosso SERVICE acessar o REPOSITORY
	//@Autowired 
	//ParkingSpotRepository parkingSpotRepository;
	
	// Obs.: Ao invés de criarmos acima, vamos setar 
	// o REPOSITORY no Construtor de nosso SERVICE
	// ---------------------------------------------
	final ParkingSpotRepository parkingSpotRepository;
	
	
	// Construtor do Service
	// ------------------------
	public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
		this.parkingSpotRepository = parkingSpotRepository;
	}

	
	// Save
	// ------------------------
	@Transactional  // Será efetuado Rollback/Commit de acordo com o sucesso da transação
	public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
		
		// Nosso repositório recebe o model a efetuar a gravação
		return parkingSpotRepository.save(parkingSpotModel);
	}


	// Find All
	public Page<ParkingSpotModel> findAll(Pageable pageable) { return parkingSpotRepository.findAll(pageable) ; }


	// Find ONE
	public Optional<ParkingSpotModel> findById(UUID id) { return parkingSpotRepository.findById(id); }

	// Delete  
	@Transactional
	public void delete(ParkingSpotModel parkingSpotModel) { parkingSpotRepository.delete(parkingSpotModel); }
	
	
	// Criptografando a senha do usuário
	// -------------------------------------
	private void encodePassword(UserModel user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	// Validações para a vaga
	// ------------------------
	public boolean existsByLicensePlateCar(String licensePlateCar) {
		
		// Método automatizado declarado na interface JPA (em nosso REPOSITORY)
		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
	}
	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		
		// Método automatizado declarado na interface JPA (em nosso REPOSITORY)
		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}
	public boolean existsByApartmentAndBlock(String apartment, String block) {
		
		// Método automatizado declarado na interface JPA (em nosso REPOSITORY)
		return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);		
	}


	
}

