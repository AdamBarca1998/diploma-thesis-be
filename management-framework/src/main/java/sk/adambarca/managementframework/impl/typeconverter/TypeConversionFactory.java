package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class TypeConversionFactory {

    private final Map<String, TypeConversionStrategy<?>> conversionMap = new HashMap<>();

    public TypeConversionFactory() {
        register(byte.class, new ByteConversionStrategy());
        register(short.class, new ShortConversionStrategy());
        register(int.class, new IntegerConversionStrategy());
        register(long.class, new LongConversionStrategy());
        register(float.class, new FloatConversionStrategy());
        register(double.class, new DoubleConversionStrategy());
        register(char.class, new CharacterConversionStrategy());
        register(boolean.class, new BooleanConversionStrategy());

        register(Byte.class, new ByteConversionStrategy());
        register(Short.class, new ShortConversionStrategy());
        register(Integer.class, new IntegerConversionStrategy());
        register(Long.class, new LongConversionStrategy());
        register(Float.class, new FloatConversionStrategy());
        register(Double.class, new DoubleConversionStrategy());
        register(Character.class, new CharacterConversionStrategy());
        register(Boolean.class, new BooleanConversionStrategy());

        register(String.class, new StringConversionStrategy());
        register(Optional.class, new OptionalConversionStrategy(this));
        register(List.class, new ListConversionStrategy(this));
        register(Set.class, new SetConversionStrategy(this));
        register(Map.class, new MapConversionStrategy(this));
        register(Record.class, new RecordConversionStrategy());
        register(Enum.class, new EnumConversionStrategy());
    }

    public void register(Class<?> clazz, TypeConversionStrategy<?> strategy) {
        conversionMap.put(clazz.getTypeName(), strategy);
    }

    public TypeConversionStrategy<?> getStrategy(Type type) {
        final var strategy = conversionMap.get(type.getTypeName());
        final var typeName = type.getTypeName();

        if (strategy == null) {
            try {
                Class<?> clazz = Class.forName(typeName);
                if (clazz.isRecord()) {
                    return conversionMap.get(Record.class.getTypeName());
                } else if (clazz.isEnum()) {
                    return conversionMap.get(Enum.class.getTypeName());
                }
            } catch (ClassNotFoundException e) {
                throw new ConversionStrategyNotFoundException("Class not found: " + typeName);
            }

            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
        }

        return strategy;
    }
}
