package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class CharacterConversionStrategy implements TypeConversionStrategy<Character> {

    @Override
    public Character convert(JsonNode json, Type type) {
        throwIfNull(json);

        final var string = json.asText();
        final var number = json.asInt();

        if (number < Character.MIN_VALUE || number > Character.MAX_VALUE) {
            throwOutRange(json);
        }

        if (string.length() > 1) {
            throwNotType(json);
        }

        return string.isEmpty() ? '\u0000' : string.charAt(0);
    }

    @Override
    public String getTypeName() {
        return "Character or char";
    }
}
