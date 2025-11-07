package com.carweb.cardemo.service.carService;

import com.carweb.cardemo.model.Car;
import com.carweb.cardemo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class GetCar {

    @Autowired
    private CarRepository carRepository;

    @GetMapping
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @GetMapping
    public Car getCarById(@RequestParam String id){
        UUID uuid = UUID.fromString(id);
        return carRepository.findById(uuid).orElse(null);
    }

    @GetMapping
    public List<Car> getCarWithParams(@RequestParam Map<String, String> params){
        List<Car> cars = this.getCars();
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
        return cars;
    }


}
