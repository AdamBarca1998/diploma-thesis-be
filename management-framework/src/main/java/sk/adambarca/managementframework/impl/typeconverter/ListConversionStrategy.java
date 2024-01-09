package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

final class ListConversionStrategy implements TypeConversionStrategy<List<?>> {

    private final TypeConversionFactory typeConversionFactory;

    public ListConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public List<?> convert(JsonNode json, Type type) {
        if (type instanceof ParameterizedType parameterizedType && json.isArray()) {
            final var subType = extractSubType(parameterizedType);
            final var valuesStrategy = typeConversionFactory.getStrategy(extractCurrentType(subType));

            Spliterator<JsonNode> spliterator = Spliterators.spliteratorUnknownSize(json.elements(), Spliterator.ORDERED);
            Stream<JsonNode> stream = StreamSupport.stream(spliterator, false);

            return stream.map(e -> valuesStrategy.convert(e, subType))
                    .toList();
        } else {
            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
        }
    }

    @Override
    public String getTypeName() {
        return "List";
    }
}