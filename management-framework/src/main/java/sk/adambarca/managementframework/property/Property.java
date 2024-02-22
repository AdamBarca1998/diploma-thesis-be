package sk.adambarca.managementframework.property;

import java.util.List;

public record Property(
        String name,
        String description,
        String type,
        Object value,
        List<String> validations
) {
}
