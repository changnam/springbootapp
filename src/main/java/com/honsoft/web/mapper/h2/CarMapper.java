package com.honsoft.web.mapper.h2;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.honsoft.web.entity.Car;

public interface CarMapper {
	@Select("SELECT * FROM cars WHERE id = #{id}")
	Car selectCar(@Param("id") int id);

	@Select("Select * from cars ")
	List<Car> selectAllCars();

	@Insert("Insert into cars (name,price) values (#{name},#{price})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertCar(Car car);

	@Update("Update cars set name = #{name} , price = #{price} where id = #{id}")
	void updateCar(Car car);

	@Delete("Delete from cars where id = #{id}")
	void deleteCar(Car car);
}