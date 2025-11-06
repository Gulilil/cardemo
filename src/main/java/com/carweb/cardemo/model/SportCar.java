package com.carweb.cardemo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class SportCar extends Car{
    private Integer horsepower;
    private Double topSpeed;

    public SportCar(String name, String brand, CarColor color,
                         Integer horsepower)  {
        super(name, brand, color);
        this.horsepower = horsepower;
    }
}
