package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;
import java.math.BigDecimal;

abstract class NumericConversionStrategy<T extends Number> implements TypeConversionStrategy<T> {

    @Override
    public T convert(JsonNode json, Type type) {
        throwIfNull(json);

        final var value = json.decimalValue();

        if (!isWholeNumber(value.doubleValue())) {
            throw new NotValidTypeException(STR."The \{json.asText()} is not of type \{getTypeName()}!");
        }

        if (value.compareTo(getMinValue()) < 0 || value.compareTo(getMaxValue()) > 0) {
            throw new NotValidTypeException(STR."The \{json.asText()} is out of range for \{getTypeName()}!");
        }

        return convertToNumeric(value.doubleValue());
    }

    protected boolean isWholeNumber(double value) {
        return value % 1 == 0;
    }

    protected abstract T convertToNumeric(double value);

    protected abstract BigDecimal getMinValue();

    protected abstract BigDecimal getMaxValue();
}
