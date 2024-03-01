package sk.adambarca.calculatorserver.resources.charts;

import java.util.List;

public record PieChart(

        List<String> labels,
        List<DataSet> datasets
) {
}
