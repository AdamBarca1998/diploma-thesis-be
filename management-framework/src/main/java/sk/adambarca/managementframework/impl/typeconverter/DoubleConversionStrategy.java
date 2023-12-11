package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;

class DoubleConversionStrategy implements TypeConversionStrategy<Double> {

    @Override
    public Double convert(String value, Type type) {
        return Double.parseDouble(value);
    }
}
