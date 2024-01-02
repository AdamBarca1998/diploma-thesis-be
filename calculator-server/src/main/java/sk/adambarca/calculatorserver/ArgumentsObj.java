package sk.adambarca.calculatorserver;

import java.util.List;

public record ArgumentsObj(
        Double value,
        List<ArgumentsObj> list
) {
}
