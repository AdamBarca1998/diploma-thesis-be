package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

import java.util.Optional;
import java.util.Set;

@MResource
public final class CalculatorMResource {

    public double sumSets(Set<Optional<Integer>> a, Set<Integer> b) {
        return a.stream().mapToInt(e -> e.orElse(0))
                .sum() + b.stream().mapToInt(e -> e).sum();
    }
}
