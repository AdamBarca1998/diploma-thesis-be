package sk.adambarca.calculatorserver.resources.charts.structure;

import java.util.List;

public record Data(

        List<String> labels,
        List<DataSet> datasets
) {
}
