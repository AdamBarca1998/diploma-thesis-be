package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class LongConversionStrategy implements TypeConversionStrategy<Long> {

    @Override
    public Long convert(JsonNode json, Type type) {
        if (json.isLong()) {
            return json.asLong();
        }

        throw new NotValidTypeException(STR."The \{json.asText()} is not type Long or long!");
    }

    @Override
    public String getTypeName() {
        return "Long or long";
    }
}
