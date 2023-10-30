package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

@MResource
public final class CalculatorMResource {

    public Double result = 0.1;

    public double sumAll(int a, double b, long c) {
        return a + b + c;
    }
}
