package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

class FloatConversionStrategy implements TypeConversionStrategy<Float> {

    @Override
    public Float convert(JsonNode json, Type type) {
        return Float.parseFloat(json.asText());
    }
}
