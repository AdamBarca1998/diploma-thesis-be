package sk.adambarca.calculatorserver.resources;

import sk.adambarca.calculatorserver.resources.charts.structure.*;
import sk.adambarca.managementframework.resource.MResource;

import java.util.ArrayList;
import java.util.List;

@MResource(
        name = "DoughnutExample",
        periodTimeMs = 60 * 1000
)
public class DoughnutExampleMResource {

    private DoughnutChart doughnutChart = new DoughnutChart(
            getData(),
            new Options(
                    true,
                    new Plugins(
                            new Legend("top"),
                            new Title(true, "Chart.js Doughnut Chart")
                    )
            )
    );

    public DoughnutChart getDoughnutChart() {
        return doughnutChart;
    }

    public boolean addData(String label, double value, String color) {
        var data = doughnutChart.data();
        
        if (data.labels().contains(label)) {
            int index = data.labels().indexOf(label);
            data.datasets().getFirst().data().set(index, value);
            data.datasets().getFirst().backgroundColor().set(index, color);
        } else {
            data.labels().add(label);
            data.datasets().getFirst().data().add(value);
            data.datasets().getFirst().backgroundColor().add(color);
        }

        return true;
    }

    private Data getData() {
        return new Data(
                new ArrayList<>(List.of("Red", "Orange", "Yellow", "Green", "Blue")),
                List.of(new DataSet(
                        "Dataset 1",
                        new ArrayList<>(List.of(40.048, 73.13, 22.159, 55.035, 80.401)),
                        new ArrayList<>(List.of("rgb(255, 98, 132)", "rgb(255, 159, 64)", "rgb(255, 205, 86)", "rgb(34, 207, 207)", "rgb(5, 155, 255)"))
                ))
        );
    }
}
