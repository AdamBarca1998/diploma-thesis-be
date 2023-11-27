package sk.adambarca.managementframework.function;

import sk.adambarca.managementframework.property.Property;

import java.util.List;

public record Function(
        String name,
        String description,
        String returnType,
        List<Property> properties,
        List<String> validations
) {
}
