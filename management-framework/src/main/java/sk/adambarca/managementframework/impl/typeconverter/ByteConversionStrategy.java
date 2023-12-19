package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class ByteConversionStrategy implements TypeConversionStrategy<Byte> {

    @Override
    public Byte convert(JsonNode json, Type type) {
        return json.numberValue().byteValue();
    }
}
