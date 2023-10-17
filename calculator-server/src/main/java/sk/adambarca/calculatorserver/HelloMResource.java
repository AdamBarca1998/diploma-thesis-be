package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.annotation.MResource;

@MResource
public class HelloMResource {

    String getString() {
        return "Hello Calculator!";
    }
}
