package sk.adambarca.calculatorserver.resources.charts.structure;

import java.util.List;

public record DataSet(
        String label,
        List<Double> data,
        List<String> backgroundColor
) {
}
