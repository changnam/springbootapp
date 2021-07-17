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
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.honsoft.web.entity.Car;
import com.honsoft.web.repository.h2.CarRepository;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
	    DataSourceTransactionManagerAutoConfiguration.class })
@EnableTransactionManagement
public class SpringbootappApplication implements CommandLineRunner{

	@Autowired
	@Qualifier("h2CarRepository")
	private com.honsoft.web.repository.h2.CarRepository h2CarRepository;
	
	@Autowired
	@Qualifier("hsqldbCarRepository")
	private com.honsoft.web.repository.hsqldb.CarRepository hsqldbCarRepository;
	
	@Autowired
	@Qualifier("mysqlCarRepository")
	private com.honsoft.web.repository.mysql.CarRepository mysqlCarRepository;
	
	@Autowired
	@Qualifier("oracleCarRepository")
	private com.honsoft.web.repository.oracle.CarRepository oracleCarRepository;
	
	@Autowired
	@Qualifier("postgresqlCarRepository")
	private com.honsoft.web.repository.postgresql.CarRepository postgresqlCarRepository;
	
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
		logger.info("Let's inspect the beans provided by Spring Boot: h2CarRepository");

		List<Car> allCars = (List<Car>) h2CarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getName());
		}
		
		logger.info("Let's inspect the beans provided by Spring Boot: hsqldbCarRepository");

		allCars = (List<Car>) hsqldbCarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getName());
		}
		
		logger.info("Let's inspect the beans provided by Spring Boot: mysqlCarRepository");

		allCars = (List<Car>) mysqlCarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getName());
		}
		
		logger.info("Let's inspect the beans provided by Spring Boot: oracleCarRepository");

		allCars = (List<Car>) oracleCarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getName());
		}
		
		logger.info("Let's inspect the beans provided by Spring Boot: postgresqlCarRepository");

		allCars = (List<Car>) postgresqlCarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getName());
		}
	}


}
