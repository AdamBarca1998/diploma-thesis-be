package sk.adambarca.managementframework.supportclasses;

import sk.adambarca.managementframework.resource.MResource;

import java.util.Optional;

@MResource
public class BasicClassesMResource {

    public String sayHello(String s) {
        return STR."Hello \{s}!";
    }

    public int addOneByEnum(int number, Operator operator) {
        return switch (operator) {
            case ADD -> number + 1;
            case SUB -> number - 1;
            default -> throw new RuntimeException("Unknown operator " + operator);
        };
    }

    public double sumOptional(double num1, Optional<Double> num2) {
        return num1 + num2.orElse(0.0);
    }
}