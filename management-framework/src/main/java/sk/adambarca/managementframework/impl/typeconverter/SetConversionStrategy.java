package sk.adambarca.managementframework.impl.typeconverter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class SetConversionStrategy implements TypeConversionStrategy<Set<?>> {

    private final TypeConversionStrategy<?> valuesStrategy;

    public SetConversionStrategy(TypeConversionStrategy<?> strategy) {
        this.valuesStrategy = strategy;
    }

    @Override
    public Set<?> convert(String value) {
        if (value.isEmpty()) {
            return Set.of();
        }

        String[] elements = value.split(",");

        return Arrays.stream(elements)
                .map(valuesStrategy::convert)
                .collect(Collectors.toSet());
    }
}