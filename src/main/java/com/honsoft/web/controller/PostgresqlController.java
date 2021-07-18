package com.honsoft.web.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.web.service.PostgresqlCarService;
import com.honsoft.web.entity.Car;

@RestController
public class PostgresqlController {
	@Autowired
	private PostgresqlCarService carService;

	@RequestMapping("/postgresqlInsert")
	public String postgresqlInsert(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCar(car);

		return "success";
	}

	@RequestMapping("/postgresqlList")
	public List<Car> postgresqlList() {
		return carService.selectAllCars();
	}

	@RequestMapping("/postgresqlUpdate")
	public String postgresqlUpdate(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCar(car);

		return "success";
	}

	@RequestMapping("/postgresqlDelete")
	public String postgresqlDelete(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCar(car);

		return "success";
	}

	@RequestMapping("/postgresqlInsertMapper")
	public String postgresqlInsertMapper(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCarMapper(car);

		return "success";
	}

	@RequestMapping("/postgresqlListMapper")
	public List<Car> postgresqlListMapper() {
		return carService.selectAllCarsMapper();
	}

	@RequestMapping("/postgresqlUpdateMapper")
	public String postgresqlUpdateMapper(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCarMapper(car);

		return "success";
	}

	@RequestMapping("/postgresqlDeleteMapper")
	public String postgresqlDeleteMapper(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCarMapper(car);

		return "success";
	}

}
