package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

final class ByteConversionStrategy implements TypeConversionStrategy<Byte> {

    @Override
    public Byte convert(JsonNode json, Type type) {
        if (json.isNull()) {
            throw new NotValidTypeException("Using 'null' directly for Byte or byte is not allowed." +
                    " If you intend to represent an optional value, it's recommended to use the Optional wrapper." +
                    " For example, you can use Optional<Byte>.");
        }

        final double value = json.numberValue().doubleValue();

        if (!isWholeNumber(value)) {
            throw new NotValidTypeException(STR."The \{json.asText()} is not of type Byte or byte!");
        }

        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
            throw new NotValidTypeException(STR."The \{json.asText()} is out of range for byte!");
        }

        return (byte) value;
    }
}
