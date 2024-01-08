package sk.adambarca.managementframework.supportclasses;

import sk.adambarca.managementframework.resource.MResource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@MResource
public final class CalculatorMResource {

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

    public double sumNestedLists(List<List<Integer>> numbers) {
        return numbers.stream()
                .flatMap(List::stream)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public double sumSets(Set<Optional<Integer>> a, Set<Integer> b) {
        return a.stream().mapToInt(e -> e.orElse(0))
                .sum() + b.stream().mapToInt(e -> e).sum();
    }

    public double sumMap(Map<String, List<Integer>> map) {
        return map.values().stream()
                .map(list -> list.stream().mapToInt(Integer::intValue).sum())
                .mapToInt(Integer::intValue)
                .sum();
    }

    public double sumMapInteger(Map<Integer, List<Integer>> map) {
        return map.values().stream()
                .map(list -> list.stream().mapToInt(Integer::intValue).sum())
                .mapToInt(Integer::intValue)
                .sum();
    }

    public double sumArgumentMProperty(Argument args) {
        double sum = 0;

        if (args != null) {
            sum += args.value();

            if (args.list() != null) {
                for (var nestedObj : args.list()) {
                    sum += sumArgumentMProperty(nestedObj);
                }
            }
        }

        return sum;
    }

    public double sumByEnum(List<Double> numbers, Operator operator) {
        return switch (operator) {
            case ADD -> numbers.stream().mapToDouble(Double::doubleValue).sum();
            case SUB -> numbers.stream().reduce(0.0, (a, b) -> a - b);
            default -> throw new RuntimeException("Unknown operator " + operator);
        };
    }
}
