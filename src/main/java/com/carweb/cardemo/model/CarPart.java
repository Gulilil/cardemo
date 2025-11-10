package com.carweb.cardemo.model;


import com.carweb.cardemo.enums.CarColor;
import com.carweb.cardemo.enums.CarPartCategory;
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
    private Boolean isDeleted = false;

    // Constructor
    public CarPart(String name, CarPartCategory category){
        this.name = name;
        this.category = category;
    }

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
    public CarPartCategory getCategory() {
        return this.category;
    }
    public Boolean isNotDeleted() {
        return !this.isDeleted;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setPrice(Double price){
        this.price = price;
    }
    public void setCategory(CarPartCategory category){
        this.category = category;
    }
    public void changeDeletionMark(){
        this.isDeleted = !this.isDeleted;
    }

}
