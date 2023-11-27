package sk.adambarca.managementframework.impl.typeconverter;

class FloatConversionStrategy implements TypeConversionStrategy<Float> {

    @Override
    public Float convert(String value) {
        return Float.parseFloat(value);
    }
}
