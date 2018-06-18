package com.simplenotesapp.simplenotesapp.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private RestTemplate restTemplate = new RestTemplate();

    @Value(value = "weather.baseUrl")
    private String baseUrl;

    @Value(value = "weather.apiKey")
    private String apiKey;

    public String getWeatherForCity(String city) {
//        String json = restTemplate.getForObject(URI.create(baseUrl + "?q=" + city + "&APPID=" + apiKey), String.class);
        String json = restTemplate.getForObject(URI.create("http://api.openweathermap.org/data/2.5/weather?q=Lodz&APPID=46815136db80aa4eb85dd7b66bd50d7b"), String.class);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        String weatherDescription = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().
                getAsJsonPrimitive("description").getAsString();
        return  weatherDescription;
    }
}
