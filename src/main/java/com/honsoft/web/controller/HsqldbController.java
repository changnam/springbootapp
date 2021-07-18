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
		return carService.selectAllCars();
	}

	@RequestMapping("/hsqldbUpdate")
	public String hsqldbUpdate(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCar(car);

		return "success";
	}

	@RequestMapping("/hsqldbDelete")
	public String hsqldbDelete(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCar(car);

		return "success";
	}

	@RequestMapping("/hsqldbInsertMapper")
	public String hsqldbInsertMapper(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCarMapper(car);

		return "success";
	}

	@RequestMapping("/hsqldbListMapper")
	public List<Car> hsqldbListMapper() {
		return carService.selectAllCarsMapper();
	}

	@RequestMapping("/hsqldbUpdateMapper")
	public String hsqldbUpdateMapper(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCarMapper(car);

		return "success";
	}

	@RequestMapping("/hsqldbDeleteMapper")
	public String hsqldbDeleteMapper(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCarMapper(car);

		return "success";
	}

}
