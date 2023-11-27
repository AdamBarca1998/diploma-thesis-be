package sk.adambarca.managementframework.impl.typeconverter;

class DoubleConversionStrategy implements TypeConversionStrategy<Double> {

    @Override
    public Double convert(String value) {
        return Double.parseDouble(value);
    }
}
