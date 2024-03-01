package sk.adambarca.calculatorserver.resources;

import sk.adambarca.calculatorserver.resources.charts.DataSet;
import sk.adambarca.calculatorserver.resources.charts.PieChart;
import sk.adambarca.managementframework.resource.MResource;

import java.time.LocalDateTime;
import java.util.List;

@MResource(
        periodTimeMs = 5000000
)
public class MemoryMResource {

    private String dateTime = LocalDateTime.now().toString();
    private PieChart memoryChart = createPieChart();

    public MemoryMResource() {
        periodUpdateDateTime();
    }

    public String getTime() {
        return dateTime;
    }

    // getters/setters

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public PieChart getMemoryChart() {
        return memoryChart;
    }

    // private

    private void periodUpdateDateTime() {
        Thread.startVirtualThread(() -> {
            while (true) {
                try {
                    dateTime = LocalDateTime.now().toString();
                    memoryChart = createPieChart();

                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private PieChart createPieChart() {
        final var total = Double.valueOf(Runtime.getRuntime().totalMemory());
        final var free = Double.valueOf(Runtime.getRuntime().freeMemory());

        final var dataSet = new DataSet(
                STR."Memory Chart}",
                List.of(total, free), List.of("rgb(255, 99, 132)", "rgb(54, 162, 235)"),
                4
        );

        return new PieChart(List.of("Total", "Free"), List.of(dataSet));
    }
}
