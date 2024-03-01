package sk.adambarca.calculatorserver.resources;

import java.util.Optional;

public record Person(
        String name,
        int age,
        Optional<Person> child
) {
}
