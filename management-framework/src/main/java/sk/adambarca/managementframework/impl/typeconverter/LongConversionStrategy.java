package sk.adambarca.managementframework.impl.typeconverter;

import java.math.BigDecimal;

final class LongConversionStrategy extends NumericConversionStrategy<Long> {

    @Override
    protected Long convertToNumeric(double value) {
        return (long) value;
    }

    @Override
    protected BigDecimal getMinValue() {
        return BigDecimal.valueOf(Long.MIN_VALUE);
    }

    @Override
    protected BigDecimal getMaxValue() {
        return BigDecimal.valueOf(Long.MAX_VALUE);
    }

    @Override
    public String getTypeName() {
        return "Long or long";
    }
}