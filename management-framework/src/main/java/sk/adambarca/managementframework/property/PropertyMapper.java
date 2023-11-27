package sk.adambarca.managementframework.property;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.List;

public class PropertyMapper {

    public Property mapToProperty(Field field) {
        return new Property(
                field.getName(),
                "",
                field.getType().getSimpleName(),
                "",
                List.of()
        );
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
