package com.aqi.aqi_calculator.dto;

public class DashboardResponse {

    private String location;
    private AqiResponse aqi;
    private WeatherResponse weather;

    public DashboardResponse(String location, AqiResponse aqi, WeatherResponse weather) {
        this.location = location;
        this.aqi = aqi;
        this.weather = weather;
    }

    public String getLocation() {
        return location;
    }

    public AqiResponse getAqi() {
        return aqi;
    }

    public WeatherResponse getWeather() {
        return weather;
    }
}
