package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;

class ShortConversionStrategy implements TypeConversionStrategy<Short> {

    @Override
    public Short convert(String value, Type type) {
        return Short.parseShort(value);
    }
}
