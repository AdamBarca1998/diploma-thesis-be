package sk.adambarca.managementframework.function;

import sk.adambarca.managementframework.property.PropertyMapper;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class FunctionMapper {

    private final PropertyMapper propertyMapper = new PropertyMapper();

    public Function mapToFunction(Method method) {
        final var properties = Arrays.stream(method.getParameters())
                .map(propertyMapper::mapToProperty)
                .toList();

        return new Function(
                method.getName(),
                "",
                method.getReturnType().getSimpleName(),
                properties,
                List.of()
        );
    }
}
