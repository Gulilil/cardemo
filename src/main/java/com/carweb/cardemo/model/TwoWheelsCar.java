package com.carweb.cardemo.model;

import com.carweb.cardemo.enums.CarColor;
import jakarta.persistence.*;

@Entity
public class TwoWheelsCar extends Car {

    private Double engineCC;
    private Boolean hasSideCar = Boolean.FALSE;

    public TwoWheelsCar() {
        super();
    }
    public TwoWheelsCar(String name, String brand, CarColor color,
                         Double engineCC)  {
        super(name, brand, color);
        this.engineCC = engineCC;
    }

    public Double getEngineCC() {
        return this.engineCC;
    }
    public Boolean setHasSideCar() {
        return this.hasSideCar;
    }
    public void setEngineCC(Double engineCC) {
        this.engineCC = engineCC;
    }
    public void setHasSideCar(Boolean hasSideCar) {
        this.hasSideCar = hasSideCar;
    }
}
