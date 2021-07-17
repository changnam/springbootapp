package com.honsoft.web.repository.mysql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.web.entity.Car;

@Repository("mysqlCarRepository")
public interface CarRepository extends CrudRepository<Car, Car>{

}
