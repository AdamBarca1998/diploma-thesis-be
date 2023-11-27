package sk.adambarca.managementframework.impl.typeconverter;

public interface TypeConversionStrategy<T> {

    T convert (String value);
}
