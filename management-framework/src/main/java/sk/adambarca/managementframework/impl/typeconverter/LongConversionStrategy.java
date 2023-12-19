package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class LongConversionStrategy implements TypeConversionStrategy<Long> {

    @Override
    public Long convert(JsonNode json, Type type) {
        return json.asLong();
    }
}
