package sk.adambarca.managementframework.impl.typeconverter;

class ByteConversionStrategy implements TypeConversionStrategy<Byte> {

    @Override
    public Byte convert(String value) {
        return Byte.parseByte(value);
    }
}
