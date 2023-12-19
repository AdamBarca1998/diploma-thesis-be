package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class BooleanConversionStrategy implements TypeConversionStrategy<Boolean> {

    @Override
    public Boolean convert(JsonNode json, Type type) {
        return json.asBoolean();
    }
}
