package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

import java.util.List;
import java.util.Optional;

@MResource
public final class CalculatorMResource {

    public double sumAll(List<Optional<Integer>> numbers) {
        return numbers.stream().mapToInt(e -> e.orElse(0))
                .sum();
    }
}
