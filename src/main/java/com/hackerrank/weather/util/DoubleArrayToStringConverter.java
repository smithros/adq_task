package com.hackerrank.weather.util;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;

@Converter
public class DoubleArrayToStringConverter implements AttributeConverter<List<Double>,String>{

    public static final String SEP = ", ";

    @Override
    public String convertToDatabaseColumn(final List<Double> attribute) {
        return attribute == null ? null : StringUtils.join(attribute, DoubleArrayToStringConverter.SEP);
    }

    @Override
    public List<Double> convertToEntityAttribute(final String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return Collections.emptyList();
        }
        try (Stream<String> stream = Arrays.stream(dbData.split(DoubleArrayToStringConverter.SEP))) {
            return stream.map(Double::parseDouble).collect(Collectors.toList());
        }
    }
}