package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

final class MapConversionStrategy implements TypeConversionStrategy<Map<?, ?>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TypeConversionFactory typeConversionFactory;

    public MapConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public Map<?, ?> convert(JsonNode json, Type type) {
        throwIfNull(json);

        if (type instanceof ParameterizedType parameterizedType && json.isObject()) {
            final var keyType = parameterizedType.getActualTypeArguments()[0];
            final var valueType = parameterizedType.getActualTypeArguments()[1];
            final var keyStrategy = typeConversionFactory.getStrategy(extractCurrentType(keyType));
            final var valueStrategy = typeConversionFactory.getStrategy(extractCurrentType(valueType));

            final Map<Object, Object> resultMap = new HashMap<>();

            final var entries = json.fields();
            while (entries.hasNext()) {
                final var entry = entries.next();

                try {
                    Object keyObject = getKeyObject(keyType, keyStrategy, entry.getKey());
                    Object valueObject = valueStrategy.convert(entry.getValue(), valueType);

                    resultMap.put(keyObject, valueObject);
                } catch (JsonProcessingException e) {
                    throw new ConversionStrategyNotFoundException(STR."Can't remap key: '\{entry}'!");
                }
            }

            return resultMap;
        } else {
            throw getNotTypeException(json);
        }
    }

    private Object getKeyObject(Type keyType, TypeConversionStrategy keyStrategy, String key) throws JsonProcessingException {
        if (keyType.getTypeName().equals(String.class.getTypeName())) {
            return keyStrategy.convert(new TextNode(key), keyType);
        } else {
            return keyStrategy.convert(objectMapper.readTree(key), keyType);
        }
    }

    @Override
    public String getTypeName() {
        return "Map";
    }
}