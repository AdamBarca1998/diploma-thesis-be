package sk.adambarca.managementframework.impl.typeconverter;

import java.util.Arrays;
import java.util.List;

class ListConversionStrategy implements TypeConversionStrategy<List<?>> {

    private final TypeConversionStrategy<?> valuesStrategy;

    public ListConversionStrategy(TypeConversionStrategy<?> strategy) {
        this.valuesStrategy = strategy;
    }

    @Override
    public List<?> convert(String value) {
        value = value.replaceAll("\\s", "");
        if (value.equals("[]")) {
            return List.of();
        }
        String trimmedValue = value.substring(1, value.length() - 1);
        String[] elements = trimmedValue.split(",");

        return Arrays.stream(elements)
                .map(valuesStrategy::convert)
                .toList();
    }
}