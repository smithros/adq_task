package com.hackerrank.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public final class WeatherException extends RuntimeException {
    private String message;
    private Throwable err;
}
