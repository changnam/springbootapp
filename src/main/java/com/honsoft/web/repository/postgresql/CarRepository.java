package com.honsoft.web.repository.postgresql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.web.entity.Car;

@Repository("postgresqlCarRepository")
public interface CarRepository extends CrudRepository<Car, Car>{

}
