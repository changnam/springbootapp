package com.honsoft.web.mapper.postgresql;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.honsoft.web.entity.Car;

@Mapper
public interface CarMapper {
	  @Select("SELECT * FROM cars WHERE id = #{id}")
	  Car getCar(@Param("id") String id);
	  
	  @Select("Select * from cars ")
	  List<Car> getAllCars();
	  
	  @Insert("Insert into cars (name,price) values (#{name},#{price})")
	  @Options(useGeneratedKeys = true, keyProperty = "id")
	  void insertCar(Car car);
	}