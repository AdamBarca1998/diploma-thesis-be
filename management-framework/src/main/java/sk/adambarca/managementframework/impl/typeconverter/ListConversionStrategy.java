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
        if (value.isEmpty()) {
            return List.of();
        }

        String[] elements = value.split(",");

        return Arrays.stream(elements)
                .map(e -> e.equals("null") ? null : e)
                .map(valuesStrategy::convert)
                .toList();
    }
}