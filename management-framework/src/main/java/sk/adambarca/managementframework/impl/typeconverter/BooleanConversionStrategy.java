package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;

class BooleanConversionStrategy implements TypeConversionStrategy<Boolean> {

    @Override
    public Boolean convert(String value, Type type) {
        return Boolean.parseBoolean(value);
    }
}
