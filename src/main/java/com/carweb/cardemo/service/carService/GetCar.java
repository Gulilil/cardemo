package com.carweb.cardemo.service.carService;

import com.carweb.cardemo.model.*;
import com.carweb.cardemo.model.Car;
import com.carweb.cardemo.repository.CarRepository;
import com.carweb.cardemo.repository.ElectricalCarRepository;
import com.carweb.cardemo.repository.SportCarRepository;
import com.carweb.cardemo.repository.TwoWheelsCarRepository;
import com.carweb.cardemo.utils.Constant;
import com.carweb.cardemo.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class GetCar {

    private final static Constant constant = new Constant();

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ElectricalCarRepository electricalCarRepository;
    @Autowired
    private TwoWheelsCarRepository twoWheelsCarRepository;
    @Autowired
    private SportCarRepository sportCarRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll().stream().filter(Car::isNotDeleted).toList();
    }
    public List<ElectricalCar> getAllElectricalCars(){
        return electricalCarRepository.findAll().stream().filter(Car::isNotDeleted).toList();
    }
    public List<TwoWheelsCar> getAllTwoWheelsCars(){
        return twoWheelsCarRepository.findAll().stream().filter(Car::isNotDeleted).toList();
    }
    public List<SportCar> getAllSportCars(){
        return sportCarRepository.findAll().stream().filter(Car::isNotDeleted).toList();
    }

    public ResponseEntity validate(Map<String, String> params){
        if (params.containsKey("category") && !this.constant.validCategories.contains(params.get("category").toString())){
            return new ResponseEntity(new IllegalArgumentException(), "Invalid car category");
        }
        if (params.containsKey("id")){
            try {
                UUID val_uuid = UUID.fromString(params.get("id").toString());
            } catch (Exception e) {
                return new ResponseEntity(new IllegalArgumentException(), "Invalid UUID format");
            }
        }

        return new ResponseEntity(new Object(), "");
    }

    @GetMapping
    public Map<String, Object> getCars(@RequestParam Map<String, String> params){
        List<Car> cars;

        ResponseEntity validationResponse = this.validate(params);
        if (!validationResponse.getIsSuccess()){
            return validationResponse.getJsonResponse();
        }

        // Detect category
        if (params.containsKey("category")){
            String categoryStr = params.get("category");
            if (categoryStr.equals("electrical")){
                cars = new ArrayList<>(this.getAllElectricalCars());
            } else if (categoryStr.equals("two_wheels")){
                cars = new ArrayList<>(this.getAllTwoWheelsCars());
            } else { // (categoryStr.equals("sport"))
                cars = new ArrayList<>(this.getAllSportCars());
            }
        } else {
            cars = this.getAllCars();
        }
        // Filter based on the parameters
        for (String key: params.keySet()){
            String val = params.get(key);
            if (key.equals("id")){
                UUID val_uuid = UUID.fromString(val);
                cars = cars.stream().filter(car -> car.getId().equals(val_uuid)).toList();
            }
            else if (key.equals("name")){
                cars = cars.stream().filter(car -> car.getName().equals(val)).toList();
            }
            else if (key.equals("brand")){
                cars = cars.stream().filter(car -> car.getBrand().equals(val)).toList();
            }
            else if (key.equals("releasedYear")){
                Integer releasedYearInteger = Integer.parseInt(val);
                cars = cars.stream().filter(car -> car.getReleasedYear().equals(releasedYearInteger)).toList();
            }
        }
        ResponseEntity response = new ResponseEntity(cars, "Retrieve success");
        return response.getJsonResponse();
    }
}
