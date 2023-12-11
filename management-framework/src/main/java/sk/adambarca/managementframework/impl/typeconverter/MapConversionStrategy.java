package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

class MapConversionStrategy implements TypeConversionStrategy<Map<?, ?>> {

    private final TypeConversionFactory typeConversionFactory;

    public MapConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public Map<?, ?> convert(JsonNode json, Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            final var keyType = parameterizedType.getActualTypeArguments()[0];
            final var valueType = parameterizedType.getActualTypeArguments()[1];
            final var keyStrategy = typeConversionFactory.getStrategy(extractCurrentType(keyType));
            final var valueStrategy = typeConversionFactory.getStrategy(extractCurrentType(valueType));

            Map<Object, Object> resultMap = new HashMap<>();

            final var entries = json.fields();
            while (entries.hasNext()) {
                final var entry = entries.next();

                Object keyObject = keyStrategy.convert(new TextNode(entry.getKey()), keyType);
                Object valueObject = valueStrategy.convert(entry.getValue(), valueType);

                resultMap.put(keyObject, valueObject);
            }

            return resultMap;
        } else {
            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
        }
    }
}