package sk.adambarca.managementframework.supportclasses;

import sk.adambarca.managementframework.resource.MResource;

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
}