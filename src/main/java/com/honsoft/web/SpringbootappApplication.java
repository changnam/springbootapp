package com.honsoft.web;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.honsoft.web.entity.Car;
import com.honsoft.web.repository.CarRepository;

@SpringBootApplication
public class SpringbootappApplication implements CommandLineRunner{

	@Autowired
	private CarRepository carRepository;
	
	private static Logger logger = LoggerFactory.getLogger(SpringbootappApplication.class);

	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootappApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("Let's inspect the beans provided by Spring Boot:");

		List<Car> allCars = (List<Car>) carRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getName());
		}
		
	}


}
