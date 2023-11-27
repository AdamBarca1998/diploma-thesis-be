package sk.adambarca.managementframework.impl.typeconverter;

import static java.lang.StringTemplate.STR;

class CharacterConversionStrategy implements TypeConversionStrategy<Character> {

    @Override
    public Character convert(String value) {
        if (value.length() != 1) {
            throw new IllegalArgumentException(STR."Argument \{value} must be character!");
        }

        return value.charAt(0);
    }
}
