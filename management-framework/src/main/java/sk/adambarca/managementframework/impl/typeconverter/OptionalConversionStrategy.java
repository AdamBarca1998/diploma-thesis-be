package sk.adambarca.managementframework.impl.typeconverter;

import java.util.Optional;

class OptionalConversionStrategy implements TypeConversionStrategy<Optional<?>> {

    private final TypeConversionStrategy<?> valueStrategy;

    public OptionalConversionStrategy(TypeConversionStrategy<?> strategy) {
        this.valueStrategy = strategy;
    }

    @Override
    public Optional<Object> convert(String value) {
        if (value == null) {
            return Optional.empty();
        }

        var convertedValue = valueStrategy.convert(value);

        return Optional.ofNullable(convertedValue);
    }
}