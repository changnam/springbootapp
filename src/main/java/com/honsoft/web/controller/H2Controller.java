package com.honsoft.web.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.web.service.H2CarService;
import com.honsoft.web.entity.Car;

@RestController
public class H2Controller {
	@Autowired
	private H2CarService carService;
	
	@RequestMapping("/h2Insert")
	public String h2Insert(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCar(car);
		
		return "success";
	}
	
	@RequestMapping("/h2List")
	public List<Car> h2List() {
		
		return carService.getAllCars();
	}

}
