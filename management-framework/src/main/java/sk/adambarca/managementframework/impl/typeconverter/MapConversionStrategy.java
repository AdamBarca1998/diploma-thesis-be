package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;
import java.util.Map;

class MapConversionStrategy implements TypeConversionStrategy<Map<?, ?>> {

    private final TypeConversionFactory typeConversionFactory;

    public MapConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public Map<?, ?> convert(JsonNode json, Type type) {
//        value = value.replaceAll("\\s", "");
//        if (value.equals("{}")) {
//            return Map.of();
//        }
//
//        if (type instanceof ParameterizedType parameterizedType) {
//            return Map.of();
//        } else {
//            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
//        }

        return Map.of();
    }
}