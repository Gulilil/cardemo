package com.carweb.cardemo.service.carService;

import com.carweb.cardemo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CreateCar {

    @Autowired
    private CarRepository carRepository;

    @PostMapping
    public Car createCar() {

    }


}
