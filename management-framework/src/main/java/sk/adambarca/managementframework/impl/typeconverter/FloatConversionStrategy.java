package sk.adambarca.managementframework.impl.typeconverter;

import java.math.BigDecimal;

final class FloatConversionStrategy extends NumericConversionStrategy<Float> {

    @Override
    protected Float convertToNumeric(double value) {
        return (float) value;
    }

    @Override
    protected BigDecimal getMinValue() {
        return new BigDecimal(Float.toString(-Float.MAX_VALUE));
    }

    @Override
    protected BigDecimal getMaxValue() {
        return new BigDecimal(Float.toString(Float.MAX_VALUE));
    }

    @Override
    public String getTypeName() {
        return "Float or float";
    }
}
