package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;

final class PojoConversionStrategy implements TypeConversionStrategy<Record> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Record convert(JsonNode json, Type type) {
        try {
            return objectMapper.treeToValue(json, (Class<Record>) type);
        } catch (IOException e) {
            throw new ConversionException(STR."Error converting JSON to type '\{type.getTypeName()}' \n\{e.getMessage()}");
        }
    }
}
