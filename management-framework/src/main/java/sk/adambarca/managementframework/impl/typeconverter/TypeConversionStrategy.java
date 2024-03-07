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
            return parameterizedType;
        }
    }

    default Type extractCurrentType(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            return parameterizedType.getRawType();
        } else {
            return type;
        }
    }

    default void throwIfNull(JsonNode json) throws NotValidTypeException {
        if (json == null || json.isNull()) {
            throw new NotValidTypeException(STR."Using 'null' directly for \{getTypeName()} is not allowed." +
                    " If you intend to represent an optional value, it's recommended to use the Optional wrapper." +
                    " For example, you can use Optional<Byte>.");
        }
    }

    default NotValidTypeException getOutRangeException(JsonNode json) {
        return new NotValidTypeException(STR."The value '\{json.asText()}' is out of range for \{getTypeName()}!");
    }

    default NotValidTypeException getNotTypeException(JsonNode json) {
        return new NotValidTypeException(STR."The value '\{json.asText()}' is not of type \{getTypeName()}!");
    }

    default boolean isWholeNumber(double value) {
        return value % 1 == 0;
    }

    String getTypeName();
}
