package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

class StringConversionStrategy implements TypeConversionStrategy<String> {

    @Override
    public String convert(JsonNode json, Type type) {
        return json.asText();
    }
}
