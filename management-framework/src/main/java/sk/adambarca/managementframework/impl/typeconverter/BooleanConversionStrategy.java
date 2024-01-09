package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class BooleanConversionStrategy implements TypeConversionStrategy<Boolean> {

    @Override
    public Boolean convert(JsonNode json, Type type) {
        if (json.isBoolean()) {
            return json.asBoolean();
        }

        throw new NotValidTypeException(STR."The \{json.asText()} is not type Integer or int!");
    }

    @Override
    public String getTypeName() {
        return "Boolean or boolean";
    }
}
