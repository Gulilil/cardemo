package com.carweb.cardemo.service.carPartService;

import com.carweb.cardemo.enums.CarPartCategory;
import com.carweb.cardemo.model.CarPart;
import com.carweb.cardemo.repository.*;
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
@RequestMapping("/car-parts")
public class GetCarPart {

    private Constant constant;

    @Autowired
    private CarPartRepository carPartRepository;

    public List<CarPart> getAllCarParts() {
        return carPartRepository.findAll().stream().filter(CarPart::isNotDeleted).toList();
    }

    public ResponseEntity validate(Map<String, String> params){
        if (params.containsKey("category") && !this.constant.carPartCategoryMap.keySet().stream().toList().contains(params.get("category").toString())){
            return new ResponseEntity(new IllegalArgumentException(), "Invalid car part category");
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
    public Map<String, Object> getCarParts(@RequestParam Map<String, String> params){
        List<CarPart> carParts = this.getAllCarParts();

        ResponseEntity validationResponse = this.validate(params);
        if (!validationResponse.getIsSuccess()){
            return validationResponse.getJsonResponse();
        }

        // Filter based on the parameters
        for (String key: params.keySet()){
            String val = params.get(key);
            if (key.equals("id")){
                UUID val_uuid = UUID.fromString(val);
                carParts = carParts.stream().filter(carPart -> carPart.getId().equals(val_uuid)).toList();
            }
            else if (key.equals("category")){
                CarPartCategory category = this.constant.carPartCategoryMap.get(val);
                carParts = carParts.stream().filter(carPart -> carPart.getCategory().equals(category)).toList();
            }
            else if (key.equals("name")){
                carParts = carParts.stream().filter(carPart -> carPart.getName().equals(val)).toList();
            }
            else if (key.equals("brand")){
                carParts = carParts.stream().filter(carPart -> carPart.getBrand().equals(val)).toList();
            }
        }
        ResponseEntity response = new ResponseEntity(carParts, "Retrieve success");
        return response.getJsonResponse();
    }
}
