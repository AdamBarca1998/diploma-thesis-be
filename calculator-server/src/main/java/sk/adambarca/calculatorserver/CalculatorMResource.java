package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

import java.util.Arrays;

@MResource
public final class CalculatorMResource {

    public Double result = 0.1;

    public double sumAll(double[] numbers) {
        return Arrays.stream(numbers).sum();
    }
}
