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
public class PutCar {

    private final static Constant constant = new Constant();

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
        return new ResponseEntity(new Object(), "");
    }


    @PutMapping("/{id}")
    public Map<String, Object> putCar(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        List<String> payloadKeys = payload.keySet().stream().toList();
        ResponseEntity validationResponse = this.validate(id, payload);
        if (!validationResponse.getIsSuccess()){
            return validationResponse.getJsonResponse();
        }

        UUID val_uuid = UUID.fromString(id);
        Car car = carRepository.findById(val_uuid).orElse(null);
        if (payload.containsKey("name")) {
            car.setName(payload.get("name").toString());
        }
        if (payload.containsKey("brand")) {
            car.setBrand(payload.get("brand").toString());
        }
        if (payload.containsKey("color")) {
            CarColor color = constant.colorStrToEnum(payload.get("color").toString());
            car.setColor(color);
        }
        if (payload.containsKey("releasedYear")) {
            car.setReleasedYear(Integer.parseInt(payload.get("releasedYear").toString()));
        }
        carRepository.save(car);

        String category = payload.get("category").toString();
        if (category.equals("electrical")){
            ElectricalCar electricalCar = electricalCarRepository.findById(val_uuid).orElse(null);
            if (payload.containsKey("battery_capacity")) {
                electricalCar.setBatteryCapacity(Integer.parseInt(payload.get("battery_capacity").toString()));
            }
            if (payload.containsKey("charging_time")) {
                electricalCar.setChargingTime(Integer.parseInt(payload.get("charging_time").toString()));
            }
            electricalCarRepository.save(electricalCar);
        }
        if (category.equals("two_wheels")){
            TwoWheelsCar twoWheelsCar = twoWheelsCarRepository.findById(val_uuid).orElse(null);
            if (payload.containsKey("engine_cc")) {
                twoWheelsCar.setEngineCC(Double.parseDouble(payload.get("engine_cc").toString()));
            }
            if (payload.containsKey("has_side_car")) {
                twoWheelsCar.setHasSideCar(Boolean.parseBoolean(payload.get("has_side_car").toString()));
            }
            twoWheelsCarRepository.save(twoWheelsCar);
        }
        if (category.equals("sport")){
            SportCar sportCar = sportCarRepository.findById(val_uuid).orElse(null);
            if (payload.containsKey("horse_power")) {
                sportCar.setHorsePower(Integer.parseInt(payload.get("battery_capacity").toString()));
            }
            if (payload.containsKey("charging_time")) {
                sportCar.setTopSpeed(Double.parseDouble(payload.get("charging_time").toString()));
            }
            sportCarRepository.save(sportCar);
        }


        ResponseEntity response = new ResponseEntity(car, "Update success");
        return response.getJsonResponse();
    }
}
