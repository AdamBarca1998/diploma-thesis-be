package sk.adambarca.managementframework.impl.typeconverter;

class StringConversionStrategy implements TypeConversionStrategy<String> {

    @Override
    public String convert(String value) {
        return value;
    }
}
