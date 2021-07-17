package com.honsoft.web.mapper.h2;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
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
	}