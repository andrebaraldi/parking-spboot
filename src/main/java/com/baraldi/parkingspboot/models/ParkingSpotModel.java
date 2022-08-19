package com.baraldi.parkingspboot.models;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity                           // Definimos como entidade JPA
@Table (name = "TB_PARKING_SPOT") // Como será o nome da tabela no BD
public class ParkingSpotModel implements Serializable {
	
	// Controle da versão serializada, utilizada pela JVM
	private static final long serialVersionUID = 1L;
	
	// ----------------------------------------------------------------------------
	// Identificadores do tipo UUID são indicados para arquiteturas distribuídas
	// evitando duplicidade de Chave
	// ----------------------------------------------------------------------------
	@Id  // Indica que é o Id da nossa entidade 
	@GeneratedValue (strategy = GenerationType.AUTO, generator = "UUID") // Auto-incremento no banco de dados
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "uuid", columnDefinition = "char(36)")
	@Type(type = "org.hibernate.type.UUIDCharType")
	private UUID id;   
	
	
	// Número da vaga (podendo ser A23, C786, etc.)
	// Não pode ser nulo, faz parte da chave da entidade
	@Column(nullable=false, unique=true, length=10)
	private String parkingSpotNumber;
	
	// Placa do Carro faz parte da chave da entidade
	@Column(nullable=false, unique=true, length=7)
	private String licensePlateCar;

	// Marca do carro
	@Column(nullable=false, length=70)
	private String brandCar;
	
	// Modelo do carro
	@Column(nullable=false, length=70)
	private String modelCar;
	
	@Column(nullable=false, length=70)
	private String colorCar;
	
	@Column(nullable=false)
	private LocalDateTime registrationDate;
	
	// Reponsável do veículo/apartamento
	@Column(nullable=false, length=130)
	private String responsibleName;
	
	// Número do apartamento
	@Column(nullable=false, length=30)
	private String apartment;
	
	// Bloco do Apartamento
	@Column(nullable=false, length=30)
	private String block;

	
	// Get/Set
	// --------------------	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getParkingSpotNumber() {
		return parkingSpotNumber;
	}

	public void setParkingSpotNumber(String parkingSpotNumber) {
		this.parkingSpotNumber = parkingSpotNumber;
	}

	public String getLicensePlateCar() {
		return licensePlateCar;
	}

	public void setLicensePlateCar(String licensePlateCar) {
		this.licensePlateCar = licensePlateCar;
	}

	public String getBrandCar() {
		return brandCar;
	}

	public void setBrandCar(String brandCar) {
		this.brandCar = brandCar;
	}

	public String getModelCar() {
		return modelCar;
	}

	public void setModelCar(String modelCar) {
		this.modelCar = modelCar;
	}

	public String getColorCar() {
		return colorCar;
	}

	public void setColorCar(String colorCar) {
		this.colorCar = colorCar;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}
	

	
}
