package com.baraldi.parkingspboot.config;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

// Anotação para indicar ao Spring que nossa classe é uma classe de configuração
@Configuration
public class DateConfig {

	// Formatação Global para Data. Usaremos o padrão internacional
	// 'T' indica que a data está em UTC
	public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	// Geramos um objeto de hora que pode ser serializado, utilizando a formatação
	// que criamos acima (uso da classe DateTimeFormatter)
	public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = 
			      new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
	
	
	@Bean 
	@Primary  // Indica questões de prioridade ao Spring
	public ObjectMapper objectMapper() {
		
		JavaTimeModule module = new JavaTimeModule();		
		module.addSerializer(LOCAL_DATETIME_SERIALIZER);
		
		// Sempre que for feita serialização, o Spring usa o objectMapper
		return new ObjectMapper().registerModule(module);
	}	
}
