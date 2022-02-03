package com.hackerrank.weather.service;

import com.hackerrank.weather.dto.WeatherDto;
import com.hackerrank.weather.exception.WeatherException;
import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class WeatherService {

    private final WeatherRepository repo;

    public Weather save(final WeatherDto dto) {
        final Weather weather = Weather.builder()
                .date(dto.getDate())
                .lat(dto.getLat())
                .lon(dto.getLon())
                .city(dto.getCity())
                .state(dto.getState())
                .temperatures(dto.getTemperatures())
                .build();
        this.repo.save(weather);
        weather.setId(this.repo.findTopByOrderByIdDesc().getId());
        return weather;
    }

    public List<Weather> select(final String date, final String city, final String sort) {
        if (date != null) {
            return this.repo.findByDate(formatDate(date));
        }
        if (city != null) {
            if (city.contains(",")) {
                return this.repo.findCities(splitString(city));
            } else {
                return this.repo.findByCityIgnoreCase(city.toUpperCase(Locale.ROOT));
            }
        }
        if (sort != null) {
            if (sort.equals("date")) {
                return this.repo.findAllByOrderByDateAsc();
            }
            if (sort.equals("-date")) {
                return this.repo.findAllByOrderByDateDesc();
            }
        }
        return this.repo.findAllByOrderByIdAsc();
    }

    private List<String> splitString(final String city) {
        return Stream.of(city.split(",", -1))
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    private Date formatDate(final String date) {
        Date parsed;
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            parsed = sdf.parse(date);
        } catch (final ParseException e) {
            throw new WeatherException(e.getMessage(), e.getCause());
        }
        return parsed;
    }

    public Weather selectById(final Integer id) {
        return repo.findById(id).orElse(null);
    }
}
