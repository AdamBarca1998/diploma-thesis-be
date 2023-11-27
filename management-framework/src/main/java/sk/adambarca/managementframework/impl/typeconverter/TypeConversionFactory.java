package sk.adambarca.managementframework.impl.typeconverter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static java.lang.StringTemplate.STR;

public class TypeConversionFactory {

    private static final String JAVA_UTIL = "java.util.";

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
    }

    public void register(Class<?> type, TypeConversionStrategy<?> strategy) {
        final var optional = STR."\{JAVA_UTIL}Optional<\{type.getTypeName()}>";

        conversionMap.put(type.getTypeName(), strategy);
        if (!type.isPrimitive()) {
            conversionMap.put(optional, new OptionalConversionStrategy(strategy));
            conversionMap.put(STR."\{JAVA_UTIL}List<\{type.getTypeName()}>", new ListConversionStrategy(strategy));
            conversionMap.put(STR."\{JAVA_UTIL}List<\{optional}>", new ListConversionStrategy(new OptionalConversionStrategy(strategy)));
        }
    }

    public TypeConversionStrategy<?> getStrategy(Type type) {
        final var strategy = conversionMap.get(type.getTypeName());
        if (strategy == null) {
            throw new ConversionStrategyNotFoundException("Strategy for type '" + type + "' not found!");
        }

        return strategy;
    }
}
