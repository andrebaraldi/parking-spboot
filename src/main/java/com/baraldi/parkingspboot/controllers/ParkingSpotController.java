package com.baraldi.parkingspboot.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baraldi.parkingspboot.dtos.ParkingSpotDto;
import com.baraldi.parkingspboot.models.ParkingSpotModel;
import com.baraldi.parkingspboot.services.ParkingSpotService;

@RestController                          // Indicando que nossa classe é o CONTROLLER
@CrossOrigin(origins="*", maxAge = 3600) // INdica que pode ser acessado de qq fonte 
@RequestMapping("/parking-spot")         // Como todos os métodos podem ser acessados a nivel 
                                         // de classe (e não somente de métodos), colocamos
										 // a url a partir daqui
public class ParkingSpotController {

	// Criando do um ponto de injeção para o nosso SERVICE
	// utilizando o construtor do CONTROLLER.
	// Fizemos o mesmo em SERVICE acessando o REPOSITORY
	final ParkingSpotService parkingSpotService;
	
	public ParkingSpotController(ParkingSpotService parkingSpotService) {
		this.parkingSpotService = parkingSpotService;
	}

	// POST
	// -------
	// @PostMapping: Não definimos a nossa uri aqui, pois já definimos a nível de classe com @RequestMapping("/parking-spot") 
	//               no início da classe. Com @PostMapping, sabemos que aqui receberá o POST de /parking-spot . 
	// @ResponseEntity: Terá a resposta do método e também o corpo dessa resposta; Usamos <Object> pois
	//                  poderá ter diferentes tipos de objetos como resposta.
	// @RequestBody: Indicado para receber ParkingSpotDto em formato JSon.
	// @Valid      : Indica que o objeto parkingSpotDto será validado com a as anotações no DTO.
	//               Em caso de erro o cliente já vai receber um BAD REQUEST.
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
		
		
		// Validações, utilizando validações do Service
		// Obs.: Também poderíamos colocar uma Custom Validation
		if (parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
		}
		if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot Number is already in use!");
		}
		if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
		}
	
		// Instanciando o MODEL	 
		// Obs.: A partir do JAVA 8, podemos declarar o tipo como var que ele vai assumir 
		//       o objeto informado no new() TipoDoObjeto...
		var parkingSpotModel = new ParkingSpotModel();
		
		// BeanUtils.copyProperties:
		// Convertendo o DTO para MODEL, pois no BD salvamos o MODEL e não o DTO.
		// Nesse momento as constraints do DTO já foram validadas
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);

		// Como o cliente não envia a data, registramos-a aqui
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")) );
		
		// Aqui retornarmos o status e corpo, após efetuar save no SERVICE
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
	}	

	
	// GET ALL
	// -----------------
	@RequestMapping(method = RequestMethod.GET) 
	@GetMapping 
	public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots (			
			                 @PageableDefault(page=0, size=10, sort="id", direction=Sort.Direction.ASC) Pageable pageable ){
		
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
	}
	
	// GET ONE
	// --------------
	// Passaremos o id a buscar, informando que nosso parâmetro é do tipo UUID
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id){
		
		// Efetuando a busca no SERVICE
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		
		// Validando se nosso Optional contém esse registro
		if (!parkingSpotModelOptional.isPresent()) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
			
		} else {
			// Veja que usamos o get da classe Optional para capturar o item retornado do SERVICE
			return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
		}
		
	}

	// DELETE
	// --------------
	// Passaremos o id a buscar, informando que nosso parâmetro é do tipo UUID
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
		
		// Efetuando a busca no SERVICE
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		
		// Validando se nosso Optional contém esse registro
		if (!parkingSpotModelOptional.isPresent()) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
			
		}
		
		// Exclui o registro
		parkingSpotService.delete(parkingSpotModelOptional.get());
		
		// Retorna a informação que exclui ok
		return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully");
		
	}
	
	
	// PUT
	// --------------
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id, 
												    @RequestBody @Valid ParkingSpotDto parkingSpotDto){
		
		// Valida se o registro existe na base com Optional proveniente do Service
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		
		if (!parkingSpotModelOptional.isPresent()) {			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");			
		} 
		
		
		/*
		 * // Aproveita o registro que existe no banco de dados var parkingSpotModel =
		 * (ParkingSpotModel) parkingSpotModelOptional.get();
		 * 
		 * // Setando campo a campo (exceto o id), com os valores que podem ter sido ou
		 * não alterados pelo request //
		 * -----------------------------------------------------------------------------
		 * ------------------------------- parkingSpotModel.setParkingSpotNumber(
		 * parkingSpotDto.getParkingSpotNumber()); parkingSpotModel.setLicensePlateCar(
		 * parkingSpotDto.getLicensePlateCar());
		 * parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
		 * parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
		 * parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
		 * parkingSpotModel.setApartment(parkingSpotDto.getApartment());
		 * parkingSpotModel.setBlock(parkingSpotDto.getBlock());
		 * 
		 * // Salva enviando o nosso Objeto como retorno(MODEL) para o SERVICE
		 * return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
		 */
		
		// Outra maneira de alterar
	    // ----------------------------~
		var parkingSpotModel = new ParkingSpotModel();
		
		// Copia as propriedades do DTO para o MODEL
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		
		// Seta o ID do MODEL (Repare o uso de get().getId(), pois estamos usando um Optional)
		parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
		
		// Mantém a data de registro de acordo com o que já existe na base
		parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
		
		// Salva enviando o nosso Objeto como retorno(MODEL) para o SERVICE
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
		
	}
	
	
	
	
		
	
}
