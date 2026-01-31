package com.aqi.aqi_calculator.service;

import com.aqi.aqi_calculator.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AqiService {

    @Value("${openweather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public DashboardResponse getDashboard(String city) {

        // 1️⃣ City → Coordinates
        Map<String, Object> coord = getCoordinates(city);
        double lat = (double) coord.get("lat");
        double lon = (double) coord.get("lon");

        String location = city;
        if (coord.get("state") != null) {
            location += ", " + coord.get("state");
        }
        location += ", " + coord.get("country");

        // 2️⃣ AQI
        AqiResponse aqi = getAqi(lat, lon);

        // 3️⃣ Weather
        WeatherResponse weather = getWeather(lat, lon);

        return new DashboardResponse(location, aqi, weather);
    }

    // ------------------ HELPERS ------------------

    private Map<String, Object> getCoordinates(String city) {

        String url =
                "https://api.openweathermap.org/geo/1.0/direct?q="
                        + city + "&limit=1&appid=" + apiKey;

        List<Map<String, Object>> response =
                restTemplate.getForObject(url, List.class);

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("City not found");
        }

        return response.get(0);
    }

    private AqiResponse getAqi(double lat, double lon) {

        String url =
                "https://api.openweathermap.org/data/2.5/air_pollution?lat="
                        + lat + "&lon=" + lon + "&appid=" + apiKey;

        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);

        Map<String, Object> data =
                ((List<Map<String, Object>>) response.get("list")).get(0);

        int value =
                (int) ((Map<String, Object>) data.get("main")).get("aqi");

        return new AqiResponse(value, getAqiStatus(value));
    }

    private WeatherResponse getWeather(double lat, double lon) {

        String url =
                "https://api.openweathermap.org/data/2.5/weather?lat="
                        + lat + "&lon=" + lon
                        + "&units=metric&appid=" + apiKey;

        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);

        Map<String, Object> main =
                (Map<String, Object>) response.get("main");
        Map<String, Object> wind =
                (Map<String, Object>) response.get("wind");

        String condition =
                ((Map<String, Object>) ((List<?>) response.get("weather")).get(0))
                        .get("main").toString();

        return new WeatherResponse(
                (double) main.get("temp"),
                (int) main.get("humidity"),
                (double) wind.get("speed"),
                condition
        );
    }

    private String getAqiStatus(int aqi) {
        return switch (aqi) {
            case 1 -> "Good";
            case 2 -> "Fair";
            case 3 -> "Moderate";
            case 4 -> "Poor";
            case 5 -> "Very Poor";
            default -> "Unknown";
        };
    }
}
