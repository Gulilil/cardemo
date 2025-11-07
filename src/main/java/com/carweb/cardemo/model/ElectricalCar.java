package com.carweb.cardemo.model;

import com.carweb.cardemo.enums.CarColor;
import jakarta.persistence.*;

@Entity
public class ElectricalCar extends Car{

    private Integer batteryCapacity;
    private Integer chargingTime;

    public ElectricalCar(String name, String brand, CarColor color,
                         Integer batteryCapacity, Integer chargingTime)  {
        super(name, brand, color);
        this.batteryCapacity = batteryCapacity;
        this.chargingTime = chargingTime;
    }
}
