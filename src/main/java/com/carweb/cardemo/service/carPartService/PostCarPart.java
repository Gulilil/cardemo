package com.carweb.cardemo.service.carPartService;

import com.carweb.cardemo.enums.CarColor;
import com.carweb.cardemo.enums.CarPartCategory;
import com.carweb.cardemo.model.*;
import com.carweb.cardemo.repository.*;
import com.carweb.cardemo.utils.Constant;
import com.carweb.cardemo.utils.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/car-parts")
public class PostCarPart {

    private final static Constant constant = new Constant();

    @Autowired
    private CarPartRepository carPartRepository;
    @Autowired
    private CarRepository carRepository;

    private ResponseEntity validate(Map<String, Object> payload) {
        List<String> payloadKeys = payload.keySet().stream().toList();
        String category = payload.get("category").toString();
        if (!this.constant.carPartCategoryMap.keySet().stream().toList().contains(category)){
            return new ResponseEntity(new IllegalArgumentException(), "Invalid car part category");
        }
        if (!this.constant.isAllRequiredItemContained(this.constant.requiredCarPartParams, payloadKeys)){
            return new ResponseEntity(new IllegalArgumentException(), "Insufficient data to construct car part");
        }
        return new ResponseEntity(new Object(), "");
    }


    @PostMapping
    public Map<String, Object> postCarPart(@RequestBody Map<String, Object> payload) {
        List<String> payloadKeys = payload.keySet().stream().toList();
        ResponseEntity validationResponse = this.validate(payload);
        if (!validationResponse.getIsSuccess()){
            return validationResponse.getJsonResponse();
        }

        String name = payload.get("name").toString();
        String categoryStr = payload.get("category").toString();
        CarPartCategory category = this.constant.carPartCategoryMap.get(categoryStr);
        CarPart newCarPart = new CarPart(name, category);

        // Insert all the other attribute
        if (payloadKeys.contains("brand")){
            String brand = payload.get("brand").toString();
            newCarPart.setBrand(brand);
        }
        if (payloadKeys.contains("price")){
            Double price = Double.parseDouble(payload.get("price").toString());
            newCarPart.setPrice(price);
        }
        carPartRepository.save(newCarPart);

        UUID car_id = UUID.fromString(payload.get("car_id").toString());
        Car car = carRepository.findById(car_id).orElse(null);
        car.addPart(newCarPart);
        carRepository.save(car);

        ResponseEntity response = new ResponseEntity(newCarPart, "Creation success");
        return response.getJsonResponse();
    }
}
