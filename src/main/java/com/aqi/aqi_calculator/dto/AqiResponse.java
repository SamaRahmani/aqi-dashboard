package com.aqi.aqi_calculator.dto;

public class AqiResponse {

    private int value;
    private String status;

    public AqiResponse(int value, String status) {
        this.value = value;
        this.status = status;
    }

    public int getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }
}
