package sk.adambarca.managementframework.impl.typeconverter;

final class ShortConversionStrategy extends NumericConversionStrategy<Short> {

    @Override
    protected Short convertToNumeric(double value) {
        return (short) value;
    }

    @Override
    protected double getMinValue() {
        return Short.MIN_VALUE;
    }

    @Override
    protected double getMaxValue() {
        return Short.MAX_VALUE;
    }

    @Override
    public String getTypeName() {
        return "Short or short";
    }
}
