package com.arunbalachandran.ehealth.converters;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;

public class UUIDToStringConverter implements Converter<UUID, String> {

    @Override
    public String convert(UUID source) {
        return source.toString();
    }
}
