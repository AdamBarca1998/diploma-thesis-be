package sk.adambarca.managementframework.testclasses;

import java.util.List;

public record ArgumentMProperty(
        Double value,
        List<ArgumentMProperty> list
) {
}
