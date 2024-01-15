package sk.adambarca.managementframework.supportclasses;

import java.util.Optional;

public record Person(
        String name,
        int age,
        Optional<Person> child
) {
}
