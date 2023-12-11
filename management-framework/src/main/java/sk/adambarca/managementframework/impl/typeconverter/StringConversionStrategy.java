package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;

class StringConversionStrategy implements TypeConversionStrategy<String> {

    @Override
    public String convert(String value, Type type) {
        return value;
    }
}
