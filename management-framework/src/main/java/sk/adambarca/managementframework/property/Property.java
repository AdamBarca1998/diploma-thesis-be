package sk.adambarca.managementframework.property;

import java.util.List;

public record Property(
        String name,
        String description,
        String type,
        String value,
        List<String> validations
) {
}
