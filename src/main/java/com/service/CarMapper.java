package com.service;

import com.model.Car;
import com.model.CarBuilder;

public class CarMapper {


    public static CarBuilder map(Car car){
        return CarBuilder.builder()
                .mark(car.getMark())
                .model(car.getModel())
                .fuel(car.getFuel())
                .yearProduction(car.getYearProduction())
                .carType(car.getCarType())
                .price(car.getPrice())
                .build();
    }
}
