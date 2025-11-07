package com.carweb.cardemo.model;

import com.carweb.cardemo.enums.CarColor;
import jakarta.persistence.*;

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
