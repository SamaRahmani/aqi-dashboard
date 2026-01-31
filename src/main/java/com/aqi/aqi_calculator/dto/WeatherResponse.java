package com.aqi.aqi_calculator.dto;

public class WeatherResponse {

    private double temp;
    private int humidity;
    private double wind;
    private String condition;

    public WeatherResponse(double temp, int humidity, double wind, String condition) {
        this.temp = temp;
        this.humidity = humidity;
        this.wind = wind;
        this.condition = condition;
    }

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWind() {
        return wind;
    }

    public String getCondition() {
        return condition;
    }
}
