package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;

class ByteConversionStrategy implements TypeConversionStrategy<Byte> {

    @Override
    public Byte convert(String value, Type type) {
        return Byte.parseByte(value);
    }
}
