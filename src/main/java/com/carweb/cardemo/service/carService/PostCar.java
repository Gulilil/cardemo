package com.carweb.cardemo.service.carService;

import com.carweb.cardemo.enums.CarColor;
import com.carweb.cardemo.model.Car;
import com.carweb.cardemo.model.ElectricalCar;
import com.carweb.cardemo.model.SportCar;
import com.carweb.cardemo.model.TwoWheelsCar;
import com.carweb.cardemo.utils.Constant;
import com.carweb.cardemo.utils.ResponseEntity;
import com.carweb.cardemo.repository.CarRepository;
import com.carweb.cardemo.repository.ElectricalCarRepository;
import com.carweb.cardemo.repository.SportCarRepository;
import com.carweb.cardemo.repository.TwoWheelsCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
public class PostCar {

    private final static Constant constant = new Constant();

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ElectricalCarRepository electricalCarRepository;
    @Autowired
    private TwoWheelsCarRepository twoWheelsCarRepository;
    @Autowired
    private SportCarRepository sportCarRepository;

    private ResponseEntity validate(Map<String, Object> payload) {
        List<String> payloadKeys = payload.keySet().stream().toList();
        if (!payloadKeys.contains("category")) {
            return new ResponseEntity(new IllegalArgumentException(),"Missing car category");
        }
        String category = payload.get("category").toString();
        if (!this.constant.validCategories.contains(category)){
            return new ResponseEntity(new IllegalArgumentException(), "Invalid car category");
        }
        if (!this.constant.isAllRequiredItemContained(this.constant.requiredCarParams, payloadKeys)){
            return new ResponseEntity(new IllegalArgumentException(), "Insufficient data to construct car");
        }
        if (category.equals("electrical")){
            if (!this.constant.isAllRequiredItemContained(this.constant.requiredElectricalCarParams, payloadKeys)){
                return new ResponseEntity(new IllegalArgumentException(), "Insufficient data to construct electrical car");
            }
        }
        if (category.equals("two_wheels")) {
            if (!this.constant.isAllRequiredItemContained(this.constant.requiredTwoWheelsCarParams, payloadKeys)) {
                return new ResponseEntity(new IllegalArgumentException(), "Insufficient data to construct two wheels car");
            }
        }
        if (category.equals("sport")) {
            if (!this.constant.isAllRequiredItemContained(this.constant.requiredSportCarParams, payloadKeys)) {
                return new ResponseEntity(new IllegalArgumentException(), "Insufficient data to construct sport car");
            }
        }
        return new ResponseEntity(new Object(), "");
    }


    @PostMapping
    public Map<String, Object> postCar(@RequestBody Map<String, Object> payload) {
        List<String> payloadKeys = payload.keySet().stream().toList();
        ResponseEntity validationResponse = this.validate(payload);
        if (!validationResponse.getIsSuccess()){
            return validationResponse.getJsonResponse();
        }

        String category = payload.get("category").toString();
        String name = payload.get("name").toString();
        String brand = payload.get("brand").toString();
        CarColor color = this.constant.colorStrToEnum(payload.get("color").toString());
        Car newCar;

        // Create car based on category
        if (category.equals("electrical")){
            Integer batteryCapacity = Integer.parseInt(payload.get("battery_capacity").toString());
            Integer chargingTime = Integer.parseInt(payload.get("charging_time").toString());
            ElectricalCar car = new ElectricalCar(name, brand, color, batteryCapacity, chargingTime);
            newCar = electricalCarRepository.save(car);
        }
        else if (category.equals("two_wheels")){
            Double engineCC = Double.parseDouble(payload.get("engine_cc").toString());
            TwoWheelsCar car = new TwoWheelsCar(name, brand, color, engineCC);
            if (payloadKeys.contains("has_side_car")){
                Boolean hasSideCar = Boolean.parseBoolean(payload.get("has_side_car").toString());
                car.setHasSideCar(hasSideCar);
            }
            newCar = twoWheelsCarRepository.save(car);
        }
        else { // category.equals("sport")
            Integer horsePower = Integer.parseInt(payload.get("horse_power").toString());
            SportCar car = new SportCar(name, brand, color, horsePower);
            if (payloadKeys.contains("top_speed")){
                Double topSpeed = Double.parseDouble(payload.get("top_speed").toString());
                car.setTopSpeed(topSpeed);
            }
            newCar = sportCarRepository.save(car);
        }

        // Insert all the other attribute
        if (payloadKeys.contains("released_year")){
            Integer releasedYear = Integer.parseInt(payload.get("released_year").toString());
            newCar.setReleasedYear(releasedYear);
        }
        if (payloadKeys.contains("price")){
            Double price = Double.parseDouble(payload.get("price").toString());
            newCar.setPrice(price);
        }
        carRepository.save(newCar);

        ResponseEntity response = new ResponseEntity(newCar, "Creation success");
        return response.getJsonResponse();
    }
}
