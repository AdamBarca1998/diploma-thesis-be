package sk.adambarca.managementframework.impl.typeconverter;

class BooleanConversionStrategy implements TypeConversionStrategy<Boolean> {

    @Override
    public Boolean convert(String value) {
        return Boolean.parseBoolean(value);
    }
}
