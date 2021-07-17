package com.honsoft.web.mapper.oracle;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.honsoft.web.entity.Car;

public interface CarMapper {
	  @Select("SELECT * FROM cars WHERE id = #{id}")
	  Car getCar(@Param("id") String id);
	  
	  @Select("Select * from cars ")
	  List<Car> getAllCars();
	  
	  @Insert("Insert into cars (id,name,price) values (cars_seq.nextval,#{name},#{price})")
	  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	  void insertCar(Car car);
	}