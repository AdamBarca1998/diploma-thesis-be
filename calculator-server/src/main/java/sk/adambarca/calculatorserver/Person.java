package sk.adambarca.calculatorserver;

import java.util.Optional;

public record Person(
        String name,
        int age,
        Optional<Person> child
) {
}
