package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface TypeConversionStrategy<T> {

    T convert(JsonNode json, Type type);

    default Type extractSubType(ParameterizedType parameterizedType) {
        Type[] typeArguments = parameterizedType.getActualTypeArguments();

        if (typeArguments.length > 0) {
            return typeArguments[0];
        } else {
            throw new TypeCantBeExtractedException("Type '" + parameterizedType + "' can't be extracted!");
        }
    }

    default Type extractCurrentType(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            return parameterizedType.getRawType();
        } else {
            return type;
        }
    }

    default boolean isWholeNumber(double value) {
        return value % 1 == 0;
    }
}
