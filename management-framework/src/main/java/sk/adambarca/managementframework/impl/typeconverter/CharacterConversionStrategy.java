package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class CharacterConversionStrategy implements TypeConversionStrategy<Character> {

    @Override
    public Character convert(JsonNode json, Type type) {
        throwIfNull(json);

        final var string = json.asText();
        final var number = json.asDouble();

        if (number < Character.MIN_VALUE || number > Character.MAX_VALUE) {
            throw getOutRangeException(json);
        }

        if ((json.numberType() == null && string.length() > 1) || !isWholeNumber(number)) {
            throw getNotTypeException(json);
        }

        if (json.numberType() == null) {
            return string.isEmpty() ? '\u0000' : string.charAt(0);
        } else {
            return (char) number;
        }
    }

    @Override
    public String getTypeName() {
        return "Character or char";
    }
}
