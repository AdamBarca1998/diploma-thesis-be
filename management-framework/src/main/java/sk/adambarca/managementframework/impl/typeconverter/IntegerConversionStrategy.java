package sk.adambarca.managementframework.impl.typeconverter;

class IntegerConversionStrategy implements TypeConversionStrategy<Integer> {

    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value);
    }
}
