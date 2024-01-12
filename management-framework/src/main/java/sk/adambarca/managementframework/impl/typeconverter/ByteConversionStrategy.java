package sk.adambarca.managementframework.impl.typeconverter;

import java.math.BigDecimal;

final class ByteConversionStrategy extends NumericConversionStrategy<Byte> {

    @Override
    protected Byte convertToNumeric(double value) {
        return (byte) value;
    }

    @Override
    protected BigDecimal getMinValue() {
        return BigDecimal.valueOf(Byte.MIN_VALUE);
    }

    @Override
    protected BigDecimal getMaxValue() {
        return BigDecimal.valueOf(Byte.MAX_VALUE);
    }

    @Override
    public String getTypeName() {
        return "Byte or byte";
    }
}