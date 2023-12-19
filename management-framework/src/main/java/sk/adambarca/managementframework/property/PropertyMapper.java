package sk.adambarca.managementframework.property;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;

public class PropertyMapper {

    public Property mapToProperty(Object mObject, Field field) {
        final var clazz = mObject.getClass();
        final String fieldName = field.getName();
        final String capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

        try {
            final var value = clazz.getMethod("get" + capitalizedFieldName).invoke(mObject);

            try {
                clazz.getMethod("set" + capitalizedFieldName, field.getType());

                // enable
                return new Property(
                        fieldName,
                        "",
                        field.getType().getSimpleName(),
                        value,
                        List.of()
                );
            } catch (NoSuchMethodException e) {
                // disable
                return new Property(
                        fieldName,
                        "",
                        field.getType().getSimpleName(),
                        value,
                        List.of("Disable")
                );
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // hide
            return null;
        }
    }

    public Property mapToProperty(Parameter param) {
        return new Property(
                param.getName(),
                "",
                param.getParameterizedType().getTypeName(),
                null,
                List.of()
        );
    }
}
