package sk.adambarca.managementframework.impl.typeconverter;

class LongConversionStrategy implements TypeConversionStrategy<Long> {

    @Override
    public Long convert(String value) {
        return Long.parseLong(value);
    }
}
