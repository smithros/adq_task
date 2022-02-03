package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    List<Weather> findAllByOrderByIdAsc();

    List<Weather> findByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") final Date date);

    List<Weather> findAllByOrderByDateAsc();

    List<Weather> findAllByOrderByDateDesc();

    @Query(
            value = "select * from weather where upper(city) in ?1",
            nativeQuery = true
    )
    List<Weather> findCities(final List<String> city);

    List<Weather> findByCityIgnoreCase(final String city);

    Weather findTopByOrderByIdDesc();
}
