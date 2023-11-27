package sk.adambarca.managementframework.impl.typeconverter;

class ShortConversionStrategy implements TypeConversionStrategy<Short> {

    @Override
    public Short convert(String value) {
        return Short.parseShort(value);
    }
}
