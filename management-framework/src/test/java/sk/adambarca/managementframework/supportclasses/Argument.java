package sk.adambarca.managementframework.supportclasses;

import java.util.List;

public record Argument(
        Double value,
        List<Argument> list
) {
}
