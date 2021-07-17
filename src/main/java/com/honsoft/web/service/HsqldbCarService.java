package com.honsoft.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.honsoft.web.entity.Car;
import com.honsoft.web.repository.hsqldb.CarRepository;

@Service
public class HsqldbCarService {
	@Autowired
	@Qualifier("hsqldbCarRepository")
	private CarRepository carRepository;
	
	public void insertCar(Car car) {
		carRepository.save(car);
	}
	
	public List<Car> getAllCars() {
		return (List<Car>) carRepository.findAll();
	}
}
