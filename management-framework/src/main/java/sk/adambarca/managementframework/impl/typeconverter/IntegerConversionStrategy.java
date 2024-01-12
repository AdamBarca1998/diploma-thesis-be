package sk.adambarca.managementframework.impl.typeconverter;

import java.math.BigDecimal;

final class IntegerConversionStrategy extends NumericConversionStrategy<Integer> {

    @Override
    protected Integer convertToNumeric(double value) {
        return (int) value;
    }

    @Override
    protected BigDecimal getMinValue() {
        return BigDecimal.valueOf(Integer.MIN_VALUE);
    }

    @Override
    protected BigDecimal getMaxValue() {
        return BigDecimal.valueOf(Integer.MAX_VALUE);
    }

    @Override
    public String getTypeName() {
        return "Integer or int";
    }
}