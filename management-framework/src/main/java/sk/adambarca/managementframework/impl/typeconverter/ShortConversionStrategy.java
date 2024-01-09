package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class ShortConversionStrategy implements TypeConversionStrategy<Short> {

    @Override
    public Short convert(JsonNode json, Type type) {
        if (json.isShort()) {
            return json.shortValue();
        }

        throw new NotValidTypeException(STR."The \{json.asText()} is not type Short or short!");
    }
}
