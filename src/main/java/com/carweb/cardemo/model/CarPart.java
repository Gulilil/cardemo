package com.carweb.cardemo.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

public class CarPart {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String brand;

    @Enumerated(EnumType.STRING)
    private CarPartCategory category;
    private Double price;

    // Getter
    public UUID getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getBrand() {
        return this.brand;
    }
    public Double getPrice(){
        return this.price;
    }
}
