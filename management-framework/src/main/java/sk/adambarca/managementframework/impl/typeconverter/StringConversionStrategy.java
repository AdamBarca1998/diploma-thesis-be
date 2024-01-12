package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class StringConversionStrategy implements TypeConversionStrategy<String> {

    @Override
    public String convert(JsonNode json, Type type) {
        throwIfNull(json);

        if (!json.isTextual()) {
            throw getNotTypeException(json);
        }

        return json.asText();
    }

    @Override
    public String getTypeName() {
        return "String";
    }
}
