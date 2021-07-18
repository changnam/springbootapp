package com.honsoft.web.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honsoft.web.service.MysqlCarService;
import com.honsoft.web.entity.Car;

@RestController
public class MysqlController {
	@Autowired
	private MysqlCarService carService;

	@RequestMapping("/mysqlInsert")
	public String mysqlInsert(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCar(car);

		return "success";
	}

	@RequestMapping("/mysqlList")
	public List<Car> mysqlList() {
		return carService.selectAllCars();
	}

	@RequestMapping("/mysqlUpdate")
	public String mysqlUpdate(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCar(car);

		return "success";
	}

	@RequestMapping("/mysqlDelete")
	public String mysqlDelete(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCar(car);

		return "success";
	}

	@RequestMapping("/mysqlInsertMapper")
	public String mysqlInsertMapper(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCarMapper(car);

		return "success";
	}

	@RequestMapping("/mysqlListMapper")
	public List<Car> mysqlListMapper() {
		return carService.selectAllCarsMapper();
	}

	@RequestMapping("/mysqlUpdateMapper")
	public String mysqlUpdateMapper(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCarMapper(car);

		return "success";
	}

	@RequestMapping("/mysqlDeleteMapper")
	public String mysqlDeleteMapper(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCarMapper(car);

		return "success";
	}

}
