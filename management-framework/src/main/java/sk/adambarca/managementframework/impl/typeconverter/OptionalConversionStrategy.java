package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

final class OptionalConversionStrategy implements TypeConversionStrategy<Optional<?>> {

    private final TypeConversionFactory typeConversionFactory;

    public OptionalConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public Optional<Object> convert(JsonNode json, Type type) {
        if (json == null || json.asText().equals("null")) {
            return Optional.empty();
        }

        if (type instanceof ParameterizedType parameterizedType) {
            final var subType = extractSubType(parameterizedType);
            final var valueStrategy = typeConversionFactory.getStrategy(extractCurrentType(subType));
            final var convertedValue = valueStrategy.convert(json, subType);

            return Optional.ofNullable(convertedValue);
        } else {
            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
        }
    }
}