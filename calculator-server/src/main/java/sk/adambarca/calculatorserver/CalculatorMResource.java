package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

import java.util.Arrays;

@MResource
public final class CalculatorMResource {

    public double sumArray(int[] numbers) {
        return Arrays.stream(numbers).sum();
    }
}
