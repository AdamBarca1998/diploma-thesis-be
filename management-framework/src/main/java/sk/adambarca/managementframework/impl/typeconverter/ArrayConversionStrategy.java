package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Array;
import java.util.Arrays;

class ArrayConversionStrategy implements TypeConversionStrategy<Object> {

    private final TypeConversionStrategy<?> valuesStrategy;
    private final Class<?> componentType;

    public ArrayConversionStrategy(TypeConversionStrategy<?> strategy, Class<?> componentType) {
        this.valuesStrategy = strategy;
        this.componentType = componentType;
    }

    @Override
    public Object convert(String value) {
        if (value.isEmpty()) {
            return Array.newInstance(componentType, 0);
        }

        String[] elements = value.split(",");

        if (componentType.isPrimitive()) {
            return convertPrimitiveArray(elements);
        } else {
            return Arrays.stream(elements)
                    .map(e -> e.equals("null") ? null : e)
                    .map(valuesStrategy::convert)
                    .toArray();
        }
    }

    private Object convertPrimitiveArray(String[] elements) {
        if (componentType == int.class) {
            return Arrays.stream(elements)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        throw new UnsupportedOperationException("Unsupported primitive type: " + componentType.getName());
    }
}