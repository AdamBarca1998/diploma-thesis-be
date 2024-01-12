package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class BooleanConversionStrategy implements TypeConversionStrategy<Boolean> {

    @Override
    public Boolean convert(JsonNode json, Type type) {
        throwIfNull(json);

        if (!json.isBoolean() && !json.isInt()) {
            throw getNotTypeException(json);
        }

        if (json.isInt()) {
            final var number = json.asInt();

            if (number == 0) {
                return false;
            }

            if (number == 1) {
                return true;
            }

            throw getNotTypeException(json);
        }

        return json.asBoolean();
    }

    @Override
    public String getTypeName() {
        return "Boolean or boolean";
    }
}
