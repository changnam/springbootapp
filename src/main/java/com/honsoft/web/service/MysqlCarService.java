package com.honsoft.web.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.honsoft.web.entity.Car;
import com.honsoft.web.mapper.mysql.CarMapper;
import com.honsoft.web.repository.mysql.CarRepository;

@Service
public class MysqlCarService {
	private Logger logger = LoggerFactory.getLogger(MysqlCarService.class);

	@Autowired
	@Qualifier("mysqlCarRepository")
	private CarRepository carRepository;

	@Autowired
	private CarMapper carMapper;

	public void insertCar(Car car) {
		Optional<Car> exist = carRepository.findById(car.getId());
		if (exist.isPresent()) {
			throw new IllegalArgumentException();
		} else {
			carRepository.save(car);
		}

		logger.debug(car.toString() + " inserted.");
	}

	public void insertCarMapper(Car car) {
		Optional<Car> exist = Optional.ofNullable(carMapper.selectCar(car.getId()));
		if (exist.isPresent()) {
			throw new IllegalArgumentException(); // duplicate exception
		} else {
			carMapper.insertCar(car);
		}

		logger.debug(car.toString() + " inserted.");
	}

	public List<Car> selectAllCars() {
		return (List<Car>) carRepository.findAll();
	}

	public List<Car> selectAllCarsMapper() {
		return (List<Car>) carMapper.selectAllCars();
	}

	public Car selectCar(int id) {

		Optional<Car> exist = carRepository.findById(id);
		if (exist.isPresent()) {
			return exist.get();
		} else {
			throw new IllegalArgumentException(); // not found exception
		}
	}

	public Car selectCarMapper(int id) {
		Optional<Car> exist = Optional.ofNullable(carMapper.selectCar(id));
		if (exist.isPresent()) {
			return exist.get();
		} else {
			throw new IllegalArgumentException(); // not found exception
		}
	}

	public void deleteCar(Car car) {
		Optional<Car> exist = carRepository.findById(car.getId());
		if (exist.isPresent()) {
			carRepository.delete(car);
		} else {
			throw new IllegalArgumentException(); // not found exception
		}
	}

	public void deleteCarMapper(Car car) {
		Optional<Car> exist = Optional.ofNullable(carMapper.selectCar(car.getId()));
		if (exist.isPresent()) {
			carMapper.deleteCar(car);
		} else {
			throw new IllegalArgumentException(); // not found exception
		}
	}

	public void updateCar(Car car) {
		Optional<Car> exist = carRepository.findById(car.getId());
		if (exist.isPresent()) {
			carRepository.save(car);
		} else {
			throw new IllegalArgumentException(); // not found exception
		}
	}

	public void updateCarMapper(Car car) {
		Optional<Car> exist = Optional.ofNullable(carMapper.selectCar(car.getId()));
		if (exist.isPresent()) {
			carMapper.updateCar(car);
		} else {
			throw new IllegalArgumentException(); // not found exception
		}
	}
}