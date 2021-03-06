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
		return carService.selectAllCars();
	}

	@RequestMapping("/h2Update")
	public String h2Update(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCar(car);

		return "success";
	}

	@RequestMapping("/h2Delete")
	public String h2Delete(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCar(car);

		return "success";
	}

	@RequestMapping("/h2InsertMapper")
	public String h2InsertMapper(@RequestParam("name") String name) {
		Car car = new Car();
		car.setName(name);
		car.setPrice(50000);
		carService.insertCarMapper(car);

		return "success";
	}

	@RequestMapping("/h2ListMapper")
	public List<Car> h2ListMapper() {
		return carService.selectAllCarsMapper();
	}

	@RequestMapping("/h2UpdateMapper")
	public String h2UpdateMapper(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("price") int price) {
		Car car = new Car();
		car.setId(id);
		car.setName(name);
		car.setPrice(price);
		carService.updateCarMapper(car);

		return "success";
	}

	@RequestMapping("/h2DeleteMapper")
	public String h2DeleteMapper(@RequestParam("id") int id) {
		Car car = new Car();
		car.setId(id);
		carService.deleteCarMapper(car);

		return "success";
	}

}
