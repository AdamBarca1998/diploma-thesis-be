package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

abstract class NumericConversionStrategy<T extends Number> implements TypeConversionStrategy<T> {

    @Override
    public T convert(JsonNode json, Type type) {
        throwIfNull(json);

        final double value = json.numberValue().doubleValue();

        if (!isWholeNumber(value)) {
            throw new NotValidTypeException(STR."The \{json.asText()} is not of type \{getTypeName()}!");
        }

        if (value < getMinValue() || value > getMaxValue()) {
            throw new NotValidTypeException(STR."The \{json.asText()} is out of range for \{getTypeName()}!");
        }

        return convertToNumeric(value);
    }

    protected boolean isWholeNumber(double value) {
        return value % 1 == 0;
    }

    protected abstract T convertToNumeric(double value);

    protected abstract double getMinValue();

    protected abstract double getMaxValue();
}
