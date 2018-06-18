package com.simplenotesapp.simplenotesapp.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private RestTemplate restTemplate;

    @Value(value = "${weather.baseUrl}")
    private String baseUrl;

    @Value(value = "${weather.apiKey}")
    private String apiKey;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getWeatherForCity(String city) {
        String json = restTemplate.getForObject(URI.create(baseUrl + "?q=" + city + "&APPID=" + apiKey), String.class);
      JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        String weatherDescription = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().
                getAsJsonPrimitive("description").getAsString();
        return  weatherDescription;
    }
}
