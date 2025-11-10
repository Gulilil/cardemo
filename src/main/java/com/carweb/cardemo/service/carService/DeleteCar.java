package com.carweb.cardemo.service.carService;

import com.carweb.cardemo.enums.CarColor;
import com.carweb.cardemo.model.Car;
import com.carweb.cardemo.model.ElectricalCar;
import com.carweb.cardemo.model.SportCar;
import com.carweb.cardemo.model.TwoWheelsCar;
import com.carweb.cardemo.repository.CarRepository;
import com.carweb.cardemo.repository.ElectricalCarRepository;
import com.carweb.cardemo.repository.SportCarRepository;
import com.carweb.cardemo.repository.TwoWheelsCarRepository;
import com.carweb.cardemo.utils.Constant;
import com.carweb.cardemo.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class DeleteCar {

    private Constant constant;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ElectricalCarRepository electricalCarRepository;
    @Autowired
    private TwoWheelsCarRepository twoWheelsCarRepository;
    @Autowired
    private SportCarRepository sportCarRepository;

    private ResponseEntity validate(String id, Map<String, Object> payload) {
        List<String> payloadKeys = payload.keySet().stream().toList();
        if (!payloadKeys.contains("category")) {
            return new ResponseEntity(new IllegalArgumentException(),"Missing car category");
        }
        String category = payload.get("category").toString();
        if (!this.constant.validCategories.contains(category)){
            return new ResponseEntity(new IllegalArgumentException(), "Invalid car category");
        }
        try {
            UUID val_uuid = UUID.fromString(id);
            Car car = carRepository.findById(val_uuid).orElse(null);
            if (car.equals(null)){
                return new ResponseEntity(new IllegalArgumentException(), "No data with the given ID");
            }
            if (category.equals("electrical")){
                ElectricalCar electricalCar = electricalCarRepository.findById(val_uuid).orElse(null);
                if (electricalCar.equals(null)){
                    return new ResponseEntity(new IllegalArgumentException(), "No data with the given ID");
                }
            }
            if (category.equals("two_wheels")){
                TwoWheelsCar twoWheelsCar = twoWheelsCarRepository.findById(val_uuid).orElse(null);
                if (twoWheelsCar.equals(null)){
                    return new ResponseEntity(new IllegalArgumentException(), "No data with the given ID");
                }
            }
            if (category.equals("sport")){
                SportCar sportCar = sportCarRepository.findById(val_uuid).orElse(null);
                if (sportCar.equals(null)){
                    return new ResponseEntity(new IllegalArgumentException(), "No data with the given ID");
                }
            }
        } catch (Exception e) {
            return new ResponseEntity(new IllegalArgumentException(), "Invalid UUID format");
        }
        return new ResponseEntity(new Object(), "");
    }


    @DeleteMapping("/{id}")
    public Map<String, Object> deleteCar(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        List<String> payloadKeys = payload.keySet().stream().toList();
        ResponseEntity validationResponse = this.validate(id, payload);
        if (!validationResponse.getIsSuccess()){
            return validationResponse.getJsonResponse();
        }

        UUID val_uuid = UUID.fromString(id);
        Car car = carRepository.findById(val_uuid).orElse(null);
        if (car.isNotDeleted()){
            car.changeDeletionMark();
        }
        carRepository.save(car);

        String category = payload.get("category").toString();
        if (category.equals("electrical")){
            ElectricalCar electricalCar = electricalCarRepository.findById(val_uuid).orElse(null);
            if (electricalCar.isNotDeleted()){
                electricalCar.changeDeletionMark();
            }
            electricalCarRepository.save(electricalCar);
        }
        if (category.equals("two_wheels")){
            TwoWheelsCar twoWheelsCar = twoWheelsCarRepository.findById(val_uuid).orElse(null);
            if (twoWheelsCar.isNotDeleted()){
                twoWheelsCar.changeDeletionMark();
            }
            twoWheelsCarRepository.save(twoWheelsCar);
        }
        if (category.equals("sport")){
            SportCar sportCar = sportCarRepository.findById(val_uuid).orElse(null);
            if (sportCar.isNotDeleted()){
                sportCar.changeDeletionMark();
            }
            sportCarRepository.save(sportCar);
        }


        ResponseEntity response = new ResponseEntity(car, "Update success");
        return response.getJsonResponse();
    }
}
