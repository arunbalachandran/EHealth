package com.arunbalachandran.ehealth.converters;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
public class UUIDToBytesConverter implements Converter<UUID, byte[]> {

    @Override
    public byte[] convert(UUID source) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(source.getMostSignificantBits());
        buffer.putLong(source.getLeastSignificantBits());
        return buffer.array();
    }
}
