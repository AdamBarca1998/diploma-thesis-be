package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;
import java.math.BigDecimal;

abstract class NumericConversionStrategy<T extends Number> implements TypeConversionStrategy<T> {

    @Override
    public T convert(JsonNode json, Type type) {
        throwIfNull(json);

        final var value = json.decimalValue();

        if (!isWholeNumber(type) && !isWholeNumber(value.doubleValue())) {
            throwNotType(json);
        }

        if (value.compareTo(getMinValue()) < 0 || value.compareTo(getMaxValue()) > 0) {
            throwOutRange(json);
        }

        return convertToNumeric(value.doubleValue());
    }

    protected boolean isWholeNumber(double value) {
        return value % 1 == 0;
    }

    protected boolean isWholeNumber(Type type) {
        return type.getTypeName().equals(Float.TYPE.getTypeName()) || type.getTypeName().equals(Double.TYPE.getTypeName());
    }

    protected abstract T convertToNumeric(double value);

    protected abstract BigDecimal getMinValue();

    protected abstract BigDecimal getMaxValue();
}
