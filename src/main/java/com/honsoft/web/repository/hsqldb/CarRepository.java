package com.honsoft.web.repository.hsqldb;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.web.entity.Car;

@Repository("hsqldbCarRepository")
public interface CarRepository extends CrudRepository<Car, Car>{

}
