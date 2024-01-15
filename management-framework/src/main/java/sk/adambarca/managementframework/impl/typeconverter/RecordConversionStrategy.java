package sk.adambarca.managementframework.impl.typeconverter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

final class RecordConversionStrategy implements TypeConversionStrategy<Record> {

    public final static Map<Class<?>, Class<?>> typesMap = Map.ofEntries(
            Map.entry(Byte.class, byte.class),
            Map.entry(Short.class, short.class),
            Map.entry(Integer.class, int.class),
            Map.entry(Long.class, long.class),
            Map.entry(Float.class, float.class),
            Map.entry(Double.class, double.class),
            Map.entry(Character.class, char.class),
            Map.entry(Boolean.class, boolean.class)
    );

    private final TypeConversionFactory typeConversionFactory;

    public RecordConversionStrategy(TypeConversionFactory typeConversionFactory) {
        this.typeConversionFactory = typeConversionFactory;
    }

    @Override
    public Record convert(JsonNode json, Type type) {
        throwIfNull(json);

        try {
            if (type instanceof Class<?> clazz) {
                final var fields = clazz.getDeclaredFields();
                final var values = Arrays.stream(fields)
                        .map(field -> {
                            final var valueStrategy = typeConversionFactory.getStrategy(field.getType());

                            try {
                                return valueStrategy.convert(json.get(field.getName()), field.getGenericType());
                            } catch (Exception e) {
                                throw new ConversionException(STR."The Property '\{field.getName()}' has error: \{e}");
                            }

                        })
                        .toList();
                final var parameterTypes = Arrays.stream(fields)
                        .map(Field::getType)
                        .toArray(Class<?>[]::new);

                final Constructor<?> constructor = clazz.getConstructor(parameterTypes);
                final var obj = constructor.newInstance(values.toArray());

                return (Record) obj;
            } else {
                throw new ConversionException(STR."Error converting JSON to type '\{type.getTypeName()}'!");
            }
        } catch (Exception e) {
            throw new ConversionException(STR."Error converting JSON to type '\{type.getTypeName()}' \n\{e.getMessage()}");
        }
    }

    @Override
    public String getTypeName() {
        return "Record";
    }
}
