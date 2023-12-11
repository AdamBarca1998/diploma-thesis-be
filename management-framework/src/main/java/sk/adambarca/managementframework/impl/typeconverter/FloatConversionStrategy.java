package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;

class FloatConversionStrategy implements TypeConversionStrategy<Float> {

    @Override
    public Float convert(String value, Type type) {
        return Float.parseFloat(value);
    }
}
