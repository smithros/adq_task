package com.hackerrank.weather.controller;

import com.hackerrank.weather.dto.WeatherDto;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/weather")
public class WeatherApiRestController {

    private final WeatherService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Weather> create(@RequestBody final WeatherDto data) {
        final Weather toSave = this.service.save(data);
        return new ResponseEntity<>(toSave, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Weather>> get(@RequestParam(required = false, name = "date") final String date,
                                             @RequestParam(required = false, name = "city") final String city,
                                             @RequestParam(required = false, name = "sort") final String sort
    ) {
        return Optional.ofNullable(this.service.select(date, city, sort))
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Weather> getById(@PathVariable("id") final Integer id) {
        final Weather data = this.service.selectById(id);
        if (data != null) {
            return ResponseEntity.ok(data);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
