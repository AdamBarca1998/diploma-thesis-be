package sk.adambarca.calculatorserver.resources.charts;

import java.util.List;

public record LineChart(

        List<String> labels,
        List<DataSet> datasets
) {
}
