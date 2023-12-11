package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;

class CharacterConversionStrategy implements TypeConversionStrategy<Character> {

    @Override
    public Character convert(String value, Type type) {
        if (value.length() > 1) {
            throw new IllegalArgumentException(STR."Argument \{value} must be character!");
        }

        return value.isEmpty() ? '\u0000' : value.charAt(0);
    }
}
