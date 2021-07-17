package com.honsoft.web.repository.h2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.web.entity.Car;

@Repository("h2CarRepository")
public interface CarRepository extends CrudRepository<Car, Car>{

}
