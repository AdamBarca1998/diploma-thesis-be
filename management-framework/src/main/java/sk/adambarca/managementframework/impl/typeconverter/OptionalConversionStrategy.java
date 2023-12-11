package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

class OptionalConversionStrategy implements TypeConversionStrategy<Optional<?>> {

    private final TypeConversionFactory typeConversionFactory;

    public OptionalConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public Optional<Object> convert(String value, Type type) {
        if (value == null || value.equals("null")) {
            return Optional.empty();
        }

        if (type instanceof ParameterizedType parameterizedType) {
            final var subType = extractSubType(parameterizedType);
            final var valueStrategy = typeConversionFactory.getStrategy(extractCurrentType(subType));
            final var convertedValue = valueStrategy.convert(value, subType);

            return Optional.ofNullable(convertedValue);
        } else {
            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
        }
    }
}