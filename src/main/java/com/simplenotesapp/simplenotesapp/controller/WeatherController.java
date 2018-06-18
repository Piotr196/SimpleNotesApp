package com.simplenotesapp.simplenotesapp.controller;

import com.simplenotesapp.simplenotesapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @RequestMapping(value = "/api/weather", method = RequestMethod.GET)
    public ResponseEntity<String> getWeatherForCity(@RequestParam String city) {
        return new ResponseEntity<>(weatherService.getWeatherForCity(city), HttpStatus.OK);
    }
}
