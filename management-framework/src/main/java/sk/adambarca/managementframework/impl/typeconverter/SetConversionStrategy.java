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
        value = value.replaceAll("\\s", "");
        if (value.equals("[]")) {
            return Set.of();
        }
        String trimmedValue = value.substring(1, value.length() - 1);
        String[] elements = trimmedValue.split(",");

        return Arrays.stream(elements)
                .map(valuesStrategy::convert)
                .collect(Collectors.toSet());
    }
}