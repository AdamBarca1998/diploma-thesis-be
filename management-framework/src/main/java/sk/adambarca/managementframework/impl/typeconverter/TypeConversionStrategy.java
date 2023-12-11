package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TypeConversionStrategy<T> {

    T convert (String value, Type type);

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

    default List<String> extractList(String value) {
        final String trimmedValue = value.substring(1, value.length() - 1);
        final Matcher matcher = Pattern.compile(",(?![^\\[]*\\])").matcher(trimmedValue);
        final List<String> elements = new ArrayList<>();

        int lastMatchEnd = 0;
        while (matcher.find()) {
            elements.add(trimmedValue.substring(lastMatchEnd, matcher.start()));
            lastMatchEnd = matcher.end();
        }
        elements.add(trimmedValue.substring(lastMatchEnd));

        return elements;
    }
}
