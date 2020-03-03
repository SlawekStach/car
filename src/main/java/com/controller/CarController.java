package com.controller;

import com.model.CarBuilder;
import com.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cars")
public class CarController {


private CarService carService;

@Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


@GetMapping("/{id}")
   public ResponseEntity<CarBuilder> getCarById(@PathVariable("id")Long id){
    return ResponseEntity.ok(carService.getCar(id));
}


}
