package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;

class LongConversionStrategy implements TypeConversionStrategy<Long> {

    @Override
    public Long convert(String value, Type type) {
        return Long.parseLong(value);
    }
}
