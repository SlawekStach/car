package com.service;

import com.model.CarBuilder;
import com.repository.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

private CarRepo carRepo;

@Autowired
    public CarService(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

public CarBuilder getCar(Long id){
    return carRepo.findById(id)
            .map(car -> CarMapper.map(car))
            .orElseThrow(()->new RuntimeException() );
}




}
