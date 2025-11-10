package com.carweb.cardemo.model;

import com.carweb.cardemo.enums.CarColor;
import jakarta.persistence.*;

@Entity
public class SportCar extends Car{
    private Integer horsePower;
    private Double topSpeed;

    public SportCar(String name, String brand, CarColor color,
                         Integer horsePower)  {
        super(name, brand, color);
        this.horsePower = horsePower;
    }

    public Integer getHorsePower() {
        return this.horsePower;
    }
    public Double getTopSpeed() {
        return this.topSpeed;
    }
    public void setHorsePower(Integer horsePower) {
        this.horsePower = horsePower;
    }
    public void setTopSpeed(Double topSpeed) {
        this.topSpeed = topSpeed;
    }

}
