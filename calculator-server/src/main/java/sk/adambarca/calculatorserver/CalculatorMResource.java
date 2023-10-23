package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.annotation.MResource;

@MResource
final public class CalculatorMResource {

    String hello() {
        return "Hello Calculator!";
    }
}
