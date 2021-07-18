package com.honsoft.web.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.web.service.OracleCarService;
import com.honsoft.web.entity.Car;

@RestController
public class OracleController {
	@Autowired
	private OracleCarService carService;

	@RequestMapping("/oracleInsert")
	public String oracleInsert(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCar(car);

		return "success";
	}

	@RequestMapping("/oracleList")
	public List<Car> oracleList() {
		return carService.selectAllCars();
	}

	@RequestMapping("/oracleUpdate")
	public String oracleUpdate(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCar(car);

		return "success";
	}

	@RequestMapping("/oracleDelete")
	public String oracleDelete(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCar(car);

		return "success";
	}

	@RequestMapping("/oracleInsertMapper")
	public String oracleInsertMapper(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCarMapper(car);

		return "success";
	}

	@RequestMapping("/oracleListMapper")
	public List<Car> oracleListMapper() {
		return carService.selectAllCarsMapper();
	}

	@RequestMapping("/oracleUpdateMapper")
	public String oracleUpdateMapper(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCarMapper(car);

		return "success";
	}

	@RequestMapping("/oracleDeleteMapper")
	public String oracleDeleteMapper(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCarMapper(car);

		return "success";
	}

}
