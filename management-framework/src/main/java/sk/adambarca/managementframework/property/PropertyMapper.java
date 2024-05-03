package sk.adambarca.managementframework.property;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PropertyMapper {

    public Property mapToProperty(Object mObject, Field field) {
        final var clazz = mObject.getClass();
        final String fieldName = field.getName();
        final String capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        final List<String> validations = new ArrayList<>();

        try {
            final var value = clazz.getMethod(STR."get\{capitalizedFieldName}").invoke(mObject);

            try {
                clazz.getMethod(STR."set\{capitalizedFieldName}", field.getType());
            } catch (NoSuchMethodException e) {
                validations.add("Disabled");
            }

            return new Property(
                    fieldName,
                    field.getType().getTypeName(),
                    value,
                    validations
            );
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // hidden
            return null;
        }
    }

    public Property mapToProperty(Field field) {
        return new Property(
                field.getName(),
                field.getGenericType().getTypeName(),
                null,
                List.of()
        );
    }

    public Property mapToProperty(Parameter param) {
        return new Property(
                param.getName(),
                param.getParameterizedType().getTypeName(),
                null,
                List.of()
        );
    }

    public List<Property> mapToProperties(Class<?> clazz) {
        if (clazz.isRecord()) {
            return Arrays.stream(clazz.getDeclaredFields())
                    .map(this::mapToProperty)
                    .toList();
        }

        return List.of();
    }
}
