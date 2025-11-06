package com.carweb.cardemo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class TwoWheelsCar extends Car {

    private Double engineCC;
    private Boolean hasSideCar;

    public TwoWheelsCar(String name, String brand, CarColor color,
                         Double engineCC)  {
        super(name, brand, color);
        this.engineCC = engineCC;
    }
}
