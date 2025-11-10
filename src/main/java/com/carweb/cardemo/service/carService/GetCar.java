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

    @GetMapping
    public ResponseEntity getCars(@RequestParam Map<String, String> params){
        List<Car> cars;

        // Detect category
        if (params.containsKey("category")){
            String categoryStr = params.get("category");
            if (categoryStr.equals("electrical")){
                cars = new ArrayList<>(this.getAllElectricalCars());
            } else if (categoryStr.equals("two_wheels")){
                cars = new ArrayList<>(this.getAllTwoWheelsCars());
            } else if (categoryStr.equals("sport")){
                cars = new ArrayList<>(this.getAllSportCars());
            } else {
                return new ResponseEntity(new IllegalArgumentException(), "Invalid car category");
            }
        } else {
            cars = this.getAllCars();
        }
        // Filter based on the parameters
        for (String key: params.keySet()){
            String val = params.get(key);
            if (key.equals("id")){
                try {
                    UUID val_uuid = UUID.fromString(val);
                    cars = cars.stream().filter(car -> car.getId().equals(val_uuid)).toList();
                } catch (Exception e) {
                    return new ResponseEntity(new IllegalArgumentException(), "Invalid UUID format");
                }
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
        return new ResponseEntity(cars, "");
    }


}
