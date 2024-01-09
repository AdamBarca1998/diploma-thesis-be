package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class FloatConversionStrategy implements TypeConversionStrategy<Float> {

    @Override
    public Float convert(JsonNode json, Type type) {
        if (json.isFloat()) {
            return json.floatValue();
        }

        throw new NotValidTypeException(STR."The \{json.asText()} is not type Float or float!");
    }
}
