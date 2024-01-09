package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class DoubleConversionStrategy implements TypeConversionStrategy<Double> {

    @Override
    public Double convert(JsonNode json, Type type) {
        if (json.isDouble()) {
            return json.asDouble();
        }

        throw new NotValidTypeException(STR."The \{json.asText()} is not type Double or double!");
    }
}
