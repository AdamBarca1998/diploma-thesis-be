package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.annotation.MResource;

import java.util.Arrays;

@MResource
public final class CalculatorMResource {

    public Double result = null;

    public double addAll(double[] numbers) {
        return Arrays.stream(numbers).sum();
    }
}
