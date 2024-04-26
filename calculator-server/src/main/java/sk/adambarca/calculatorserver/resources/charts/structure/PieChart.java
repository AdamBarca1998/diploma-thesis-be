package sk.adambarca.calculatorserver.resources.charts.structure;

import sk.adambarca.calculatorserver.resources.charts.structure.DataSet;

import java.util.List;

public record PieChart(

        List<String> labels,
        List<DataSet> datasets
) {
}
