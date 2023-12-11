package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;

class SetConversionStrategy implements TypeConversionStrategy<Set<?>> {

    private final TypeConversionFactory typeConversionFactory;

    public SetConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public Set<?> convert(JsonNode json, Type type) {
        final var value = json.asText().replaceAll("\\s", "");
        if (value.equals("[]")) {
            return Set.of();
        }

        if (type instanceof ParameterizedType parameterizedType) {
            final var subType = extractSubType(parameterizedType);
            final var valuesStrategy = typeConversionFactory.getStrategy(extractCurrentType(subType));

            return extractList(value).stream()
                    .map(e -> valuesStrategy.convert(new TextNode(e), subType))
                    .collect(Collectors.toSet());
        } else {
            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
        }
    }
}