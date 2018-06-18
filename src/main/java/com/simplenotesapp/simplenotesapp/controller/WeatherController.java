package com.simplenotesapp.simplenotesapp.controller;

import com.simplenotesapp.simplenotesapp.dto.NoteDto;
import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.service.WeatherService;
import com.simplenotesapp.simplenotesapp.sorting.generic.SortingOrder;
import com.simplenotesapp.simplenotesapp.sorting.notes.NotesSortingSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
