package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

import java.util.Optional;

@MResource
public final class CalculatorMResource {

    public double sumOptionals(
            Optional<Double> a,
            Optional<Integer> b,
            Optional<Float> c
    ) {
        return a.orElse(0.0) + b.orElse(0) + c.orElse(0.0f);
    }
}
