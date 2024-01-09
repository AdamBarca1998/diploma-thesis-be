package sk.adambarca.managementframework.impl.typeconverter;

final class ByteConversionStrategy extends NumericConversionStrategy<Byte> {

    @Override
    protected Byte convertToNumeric(double value) {
        return (byte) value;
    }

    @Override
    protected double getMinValue() {
        return Byte.MIN_VALUE;
    }

    @Override
    protected double getMaxValue() {
        return Byte.MAX_VALUE;
    }

    @Override
    public String getTypeName() {
        return "Byte or byte";
    }
}