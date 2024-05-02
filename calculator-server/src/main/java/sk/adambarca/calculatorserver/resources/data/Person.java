package sk.adambarca.calculatorserver.resources.data;

import java.util.Optional;

public record Person(
        String name,
        int age,
        Optional<Person> child
) {
}
