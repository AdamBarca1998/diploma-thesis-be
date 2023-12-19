package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class IntegerConversionStrategy implements TypeConversionStrategy<Integer> {

    @Override
    public Integer convert(JsonNode json, Type type) {
        return json.asInt();
    }
}
