package sk.adambarca.calculatorserver.resources.charts.structure;

import java.util.List;

public record LineChart(

        List<String> labels,
        List<DataSet> datasets
) {
}
