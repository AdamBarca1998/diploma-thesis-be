package sk.adambarca.managementframework.impl.typeconverter;

import java.math.BigDecimal;

final class ShortConversionStrategy extends NumericConversionStrategy<Short> {

    @Override
    protected Short convertToNumeric(double value) {
        return (short) value;
    }

    @Override
    protected BigDecimal getMinValue() {
        return BigDecimal.valueOf(Short.MIN_VALUE);
    }

    @Override
    protected BigDecimal getMaxValue() {
        return BigDecimal.valueOf(Short.MAX_VALUE);
    }

    @Override
    public String getTypeName() {
        return "Short or short";
    }
}
