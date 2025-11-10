package com.carweb.cardemo.utils;

import com.carweb.cardemo.enums.CarColor;
import com.carweb.cardemo.enums.CarPartCategory;

import java.util.List;
import java.util.Map;

public class Constant {

    public List<String> validCategories = List.of("electrical", "two_wheels", "sport");
    public Map<String, CarColor> carColorMap = Map.of(  "black", CarColor.BLACK,
                                                        "white", CarColor.WHITE,
                                                        "gray", CarColor.GRAY,
                                                        "blue", CarColor.BLUE,
                                                        "red", CarColor.RED,
                                                        "yellow", CarColor.YELLOW);
    public Map<String, CarPartCategory> carPartCategoryMap = Map.of (   "powerplant", CarPartCategory.POWERPLANT,
                                                                        "body", CarPartCategory.BODY,
                                                                        "interior", CarPartCategory.INTERIOR,
                                                                        "electrical", CarPartCategory.ELECTRICAL,
                                                                        "suspension", CarPartCategory.SUSPENSION,
                                                                        "brake", CarPartCategory.BRAKE);

    public List<String> requiredCarParams  = List.of("name", "brand", "color");
    public List<String> requiredElectricalCarParams = List.of("battery_capacity", "charging_time");
    public List<String> requiredTwoWheelsCarParams = List.of("engine_cc");
    public List<String> requiredSportCarParams = List.of("horse_power");
    public List<String> requiredCarPartParams = List.of("name", "category", "car_id");

    public CarColor colorStrToEnum (String color) {
        return this.carColorMap.get(color);
    }
    public CarPartCategory carPartCategoryStrToEnum (String carPartCategory) {
        return this.carPartCategoryMap.get(carPartCategory);
    }
    public Boolean isAllRequiredItemContained(List<String> requiredItemList, List<String> itemList){
        for (String requiredItem : requiredItemList){
            if (!itemList.contains(requiredItem)){
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

}
