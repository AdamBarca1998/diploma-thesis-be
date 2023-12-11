package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;

class IntegerConversionStrategy implements TypeConversionStrategy<Integer> {

    @Override
    public Integer convert(String value, Type type) {
        return Integer.parseInt(value);
    }
}
