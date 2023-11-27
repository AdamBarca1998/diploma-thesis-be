package sk.adambarca.managementframework.resource;

import sk.adambarca.managementframework.function.Function;
import sk.adambarca.managementframework.property.Property;

import java.util.List;

public record Resource(
        String name,
        String description,
        String type,
        List<Property> properties,
        List<Function> functions,
        List<String> validations
) {
}
