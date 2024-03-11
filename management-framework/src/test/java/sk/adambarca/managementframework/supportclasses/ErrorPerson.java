package sk.adambarca.managementframework.supportclasses;

import java.util.Optional;

public record ErrorPerson(
        Optional<String> name,
        double age,
        Optional<ErrorPerson> child
) {
}
