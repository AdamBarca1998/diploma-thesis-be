package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

import java.util.List;

@MResource
public final class CalculatorMResource {

    private ArgumentsObj argumentsObj = new ArgumentsObj(
            1.0,
            List.of(
                    new ArgumentsObj(2.0, List.of()),
                    new ArgumentsObj(3.0, List.of())
            )
    );


    private double sumMap(ArgumentsObj args) {
        double sum = 0;

        if (args != null) {
            sum += args.value();

            if (args.list() != null) {
                for (ArgumentsObj nestedObj : args.list()) {
                    sum += sumMap(nestedObj);
                }
            }
        }

        return sum;
    }

    public double sumMem() {
        return sumMap(argumentsObj);
    }

    public void setArgumentsObj(ArgumentsObj argumentsObj) {
        this.argumentsObj = argumentsObj;
    }

    public ArgumentsObj getArgumentsObj() {
        return this.argumentsObj;
    }
}
