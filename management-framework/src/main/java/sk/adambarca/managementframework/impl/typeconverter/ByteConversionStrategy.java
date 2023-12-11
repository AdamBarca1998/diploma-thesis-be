package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

class ByteConversionStrategy implements TypeConversionStrategy<Byte> {

    @Override
    public Byte convert(JsonNode json, Type type) {
        return Byte.parseByte(json.asText());
    }
}
