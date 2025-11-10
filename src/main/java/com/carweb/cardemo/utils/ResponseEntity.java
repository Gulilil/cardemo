package com.carweb.cardemo.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntity {
    private final Exception exceptionType;
    private final String message;
    private final Object data;
    private final Boolean isSuccess;

    public ResponseEntity(Exception exceptionType, String message){
        this.data = null;
        this.message = message;
        this.exceptionType = exceptionType;
        this.isSuccess = Boolean.FALSE;
    }

    public ResponseEntity(Object data, String message){
        this.data = data;
        this.message = message;
        this.exceptionType = null;
        this.isSuccess = Boolean.TRUE;
    }

    public Map<String, Object> getJsonResponse() {
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("data", this.data);
        responseJson.put("type", this.exceptionType.getClass().getSimpleName());
        responseJson.put("message", this.message);
        responseJson.put("isSuccess", this.isSuccess);
        return responseJson;
    }

    public Boolean getIsSuccess(){
        return this.isSuccess;
    }
}
