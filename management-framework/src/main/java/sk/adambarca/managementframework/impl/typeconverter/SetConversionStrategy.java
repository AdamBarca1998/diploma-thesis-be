package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

final class SetConversionStrategy implements TypeConversionStrategy<Set<?>> {

    private final TypeConversionFactory typeConversionFactory;

    public SetConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public Set<?> convert(JsonNode json, Type type) {
        if (type instanceof ParameterizedType parameterizedType && json.isArray()) {
            final var subType = extractSubType(parameterizedType);
            final var valuesStrategy = typeConversionFactory.getStrategy(extractCurrentType(subType));

            Spliterator<JsonNode> spliterator = Spliterators.spliteratorUnknownSize(json.elements(), Spliterator.ORDERED);
            Stream<JsonNode> stream = StreamSupport.stream(spliterator, false);

            return stream.map(e -> valuesStrategy.convert(e, subType))
                    .collect(Collectors.toSet());
        } else {
            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
        }
    }
}