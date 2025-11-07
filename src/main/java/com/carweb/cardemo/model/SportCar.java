package com.carweb.cardemo.model;

import com.carweb.cardemo.enums.CarColor;
import jakarta.persistence.*;

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
