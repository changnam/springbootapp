package com.honsoft.web.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.web.service.HsqldbCarService;
import com.honsoft.web.entity.Car;

@RestController
public class HsqldbController {
	@Autowired
	private HsqldbCarService carService;
	
	@RequestMapping("/hsqldbInsert")
	public String hsqldbInsert(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCar(car);
		
		return "success";
	}
	
	@RequestMapping("/hsqldbList")
	public List<Car> hsqldbList() {
		
		return carService.getAllCars();
	}

}
