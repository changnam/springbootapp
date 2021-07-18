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
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.honsoft.web.entity.Car;
import com.honsoft.web.mapper.h2.CarMapper;
import com.honsoft.web.repository.h2.CarRepository;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@EnableTransactionManagement
//@ImportResource(value="classpath:hsql_cfg.xml")
public class SpringbootappApplication implements CommandLineRunner {

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

	@Autowired
	private com.honsoft.web.mapper.h2.CarMapper h2CarMapper;
	@Autowired
	private com.honsoft.web.mapper.mysql.CarMapper mysqlCarMapper;
	@Autowired
	private com.honsoft.web.mapper.hsqldb.CarMapper hsqldbCarMapper;
	@Autowired
	private com.honsoft.web.mapper.oracle.CarMapper oracleCarMapper;
	@Autowired
	private com.honsoft.web.mapper.postgresql.CarMapper postgresqlCarMapper;

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

		Car hyundaiCar = new Car();
		hyundaiCar.setName("H2Hyundai");
		hyundaiCar.setPrice(1000000);
		h2CarRepository.save(hyundaiCar);
		List<Car> allCars = (List<Car>) h2CarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}

		logger.info("Let's inspect the beans provided by Spring Boot: hsqldbCarRepository");

		hyundaiCar.setId(0);
		hyundaiCar.setName("HsqldbHyundai");
		hyundaiCar.setPrice(1000000);
		hsqldbCarRepository.save(hyundaiCar);
		allCars = (List<Car>) hsqldbCarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}

		logger.info("Let's inspect the beans provided by Spring Boot: mysqlCarRepository");

		hyundaiCar.setId(0);
		hyundaiCar.setName("MysqlHyundai");
		hyundaiCar.setPrice(1000000);
		mysqlCarRepository.save(hyundaiCar);
		allCars = (List<Car>) mysqlCarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}

		logger.info("Let's inspect the beans provided by Spring Boot: oracleCarRepository");

		// hyundaiCar.setId(0);
		// hyundaiCar.setName("OracleHyundai");
		// hyundaiCar.setPrice(1000000);
		// oracleCarRepository.save(hyundaiCar);
		allCars = (List<Car>) oracleCarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}

		logger.info("Let's inspect the beans provided by Spring Boot: postgresqlCarRepository");

		hyundaiCar.setId(0);
		hyundaiCar.setName("PostgresqlHyundai");
		hyundaiCar.setPrice(1000000);
		postgresqlCarRepository.save(hyundaiCar);
		allCars = (List<Car>) postgresqlCarRepository.findAll();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}

		logger.info("Let's inspect the beans provided by Spring Boot: h2CarMapper");

		Car kiaCar = new Car();
		kiaCar.setName("H2Kia");
		kiaCar.setPrice(3000000);

		h2CarMapper.insertCar(kiaCar);
		allCars = (List<Car>) h2CarMapper.selectAllCars();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}

		logger.info("Let's inspect the beans provided by Spring Boot: hsqldbCarMapper");

		kiaCar.setId(0);
		kiaCar.setName("HsqldbKia");
		kiaCar.setPrice(3000000);
		hsqldbCarMapper.insertCar(kiaCar);
		allCars = (List<Car>) hsqldbCarMapper.selectAllCars();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}

		logger.info("Let's inspect the beans provided by Spring Boot: mysqlCarMapper");

		kiaCar.setId(0);
		kiaCar.setName("MysqlKia");
		kiaCar.setPrice(3000000);
		mysqlCarMapper.insertCar(kiaCar);
		allCars = (List<Car>) mysqlCarMapper.selectAllCars();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}

		logger.info("Let's inspect the beans provided by Spring Boot: oracleCarMapper");

		kiaCar.setId(0);
		kiaCar.setName("OracleKia");
		kiaCar.setPrice(3000000);
		oracleCarMapper.insertCar(kiaCar);
		allCars = (List<Car>) oracleCarMapper.selectAllCars();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}

		logger.info("Let's inspect the beans provided by Spring Boot: postgresqlCarMapper");

		kiaCar.setId(0);
		kiaCar.setName("PostgresqlKia");
		kiaCar.setPrice(3000000);
		postgresqlCarMapper.insertCar(kiaCar);
		allCars = (List<Car>) postgresqlCarMapper.selectAllCars();
		for (Car car : allCars) {
			logger.info(car.getId() + "," + car.getName());
		}
	}

}
