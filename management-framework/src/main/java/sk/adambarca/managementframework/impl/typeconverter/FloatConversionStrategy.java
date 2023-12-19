package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class FloatConversionStrategy implements TypeConversionStrategy<Float> {

    @Override
    public Float convert(JsonNode json, Type type) {
        return json.floatValue();
    }
}
