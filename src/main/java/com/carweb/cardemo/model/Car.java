package com.carweb.cardemo.model;

import com.carweb.cardemo.enums.CarColor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Car {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Integer releasedYear;
    private String brand;

    @Enumerated(EnumType.STRING)
    private CarColor color;
    private Double price;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CarPart> parts = new ArrayList<CarPart>();

    // Constructor
    public Car (String name, String brand, CarColor color) {
        this.name = name;
        this.brand = brand;
        this.color = color;
    }

    // Getter
    public UUID getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public Integer getReleasedYear() {
        return this.releasedYear;
    }
    public CarColor getColor() {
        return this.color;
    }
    public String getBrand() {
        return this.brand;
    }
    public Double getPrice(){
        return this.price;
    }
    public List<CarPart> getParts() {
        return this.parts;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setReleasedYear(Integer releasedYear) {
        this.releasedYear = releasedYear;
    }
    public void setColor(CarColor color) {
        this.color = color;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setPrice(Double price){
        this.price = price;
    }

    // Manage Parts
    public void addPart(CarPart part){
        this.parts.add(part);
    }
    public void removePartByName (String name){
        this.parts.removeIf(part -> part.getName().equals(name));
    }
    public void removePartById (UUID id) {
        this.parts.removeIf(part -> part.getId().equals(id));
    }
}
