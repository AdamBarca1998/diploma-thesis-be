package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;
import java.math.BigDecimal;

abstract class NumericConversionStrategy<T extends Number> implements TypeConversionStrategy<T> {

    @Override
    public T convert(JsonNode json, Type type) {
        throwIfNull(json);

        if (json.numberType() == null) {
            throw getNotTypeException(json);
        }

        final var value = json.decimalValue();

        if (isWholeType(type) && !isWholeNumber(value.doubleValue())) {
            throw getNotTypeException(json);
        }

        if (value.compareTo(getMinValue()) < 0 || value.compareTo(getMaxValue()) > 0) {
            throw getOutRangeException(json);
        }

        return convertToNumeric(value.doubleValue());
    }

    protected boolean isWholeType(Type type) {
        return !(
                type.getTypeName().equals(Float.TYPE.getTypeName()) || type.getTypeName().equals(Float.class.getTypeName()) ||
                type.getTypeName().equals(Double.TYPE.getTypeName()) || type.getTypeName().equals(Double.class.getTypeName())
        );
    }

    protected abstract T convertToNumeric(double value);

    protected abstract BigDecimal getMinValue();

    protected abstract BigDecimal getMaxValue();
}
