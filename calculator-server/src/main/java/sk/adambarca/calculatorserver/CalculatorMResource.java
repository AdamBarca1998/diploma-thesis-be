package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

@MResource
public final class CalculatorMResource {

    public Double result = 0.1;

    public double sumAllPrimitives(
            byte _byte,
            short _short,
            int _int,
            long _long,
            float _float,
            double _double,
            char _char,
            boolean _boolean
    ) {
        final var result = _byte + _short + _int + _long + _float + _double + _char;

        if (_boolean) {
            return result + 1;
        }

        return result;
    }
}
