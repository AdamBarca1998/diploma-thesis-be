package sk.adambarca.managementframework.impl.typeconverter;

import java.math.BigDecimal;

final class DoubleConversionStrategy extends NumericConversionStrategy<Double> {

    @Override
    protected Double convertToNumeric(double value) {
        return value;
    }

    @Override
    protected BigDecimal getMinValue() {
        return BigDecimal.valueOf(Double.MIN_VALUE);
    }

    @Override
    protected BigDecimal getMaxValue() {
        return BigDecimal.valueOf(Double.MAX_VALUE);
    }

    @Override
    public String getTypeName() {
        return "Double or double";
    }
}