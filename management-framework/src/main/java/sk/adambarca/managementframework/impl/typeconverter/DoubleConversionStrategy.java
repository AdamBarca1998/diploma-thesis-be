package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

class DoubleConversionStrategy implements TypeConversionStrategy<Double> {

    @Override
    public Double convert(JsonNode json, Type type) {
        return json.asDouble();
    }
}
