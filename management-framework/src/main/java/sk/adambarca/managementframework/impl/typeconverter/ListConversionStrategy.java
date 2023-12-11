package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

class ListConversionStrategy implements TypeConversionStrategy<List<?>> {

    private final TypeConversionFactory typeConversionFactory;

    public ListConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public List<?> convert(JsonNode json, Type type) {
        var value = json.asText().replaceAll("\\s", "");
        if (value.equals("[]")) {
            return List.of();
        }

        if (type instanceof ParameterizedType parameterizedType) {
            final var subType = extractSubType(parameterizedType);
            final var valuesStrategy = typeConversionFactory.getStrategy(extractCurrentType(subType));

            return extractList(value).stream()
                    .map(e -> valuesStrategy.convert(new TextNode(e), subType))
                    .toList();
        } else {
            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
        }
    }
}