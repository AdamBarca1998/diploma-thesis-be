package sk.adambarca.managementframework.testclasses;

import sk.adambarca.managementframework.resource.MResource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public Double sumAllPrimitiveWrappers(
            Byte _byte,
            Short _short,
            Integer _int,
            Long _long,
            Float _float,
            Double _double,
            Character _char,
            Boolean _boolean
    ) {
        final var result = _byte + _short + _int + _long + _float + _double + _char;

        if (_boolean) {
            return result + 1;
        }

        return result;
    }

    public double sumOptionals(
            Optional<Double> a,
            Optional<Integer> b,
            Optional<Float> c
    ) {
        return a.orElse(0.0) + b.orElse(0) + c.orElse(0.0f);
    }

    public double sum(List<Optional<Integer>> numbers) {
        return numbers.stream().mapToInt(e -> e.orElse(0))
                .sum();
    }

    public double sumArrays(int[] ints, Integer[] integers, Optional<Boolean>[] booleans) {
        return Arrays.stream(ints).sum() +
                Arrays.stream(integers).reduce(0, Integer::sum) +
                Arrays.stream(booleans)
                        .map(b -> Boolean.compare(b.orElse(false), true))
                        .reduce(0, Integer::sum)
                ;
    }
}
