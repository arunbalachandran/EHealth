package com.arunbalachandran.ehealth.converters;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;

public class StringToUUIDConverter implements Converter<String, UUID> {

    @Override
    public UUID convert(String source) {
        return UUID.fromString(source);
    }
}
