package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;

final class EnumConversionStrategy implements TypeConversionStrategy<Enum<?>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Enum<?> convert(JsonNode json, Type type) {
        try {
            return objectMapper.treeToValue(json, (Class<Enum>) type);
        } catch (IOException e) {
            throw new ConversionException(STR."Error converting JSON to type '\{type.getTypeName()}' \n\{e.getMessage()}");
        }
    }

    @Override
    public String getTypeName() {
        return "Enum";
    }
}
