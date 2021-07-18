package com.honsoft.web.repository.oracle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.web.entity.Car;

@Repository("oracleCarRepository")
public interface CarRepository extends CrudRepository<Car, Integer> {

}
