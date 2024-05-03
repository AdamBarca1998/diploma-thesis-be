package sk.adambarca.calculatorserver.resources;

import sk.adambarca.calculatorserver.resources.charts.*;
import sk.adambarca.calculatorserver.resources.charts.structure.LineChart;
import sk.adambarca.calculatorserver.resources.charts.structure.PieChart;
import sk.adambarca.managementframework.resource.MResource;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@MResource(
        name = "Charts example",
        periodTimeMs = 60 * 1000
)
public class ChartExampleMResource {

    private PieChart fileStore;
    private LineChart cpu;
    private LineChart cpu1;
    private LineChart cpu2;
    private final CpuChart cpuChart = new CpuChart();
    private final FileStoreChart fileStoreChart = new FileStoreChart();

    public ChartExampleMResource() {
        periodUpdates();
    }

    public PieChart getFileStore() {
        return fileStore;
    }

    public LineChart getCpu() {
        return cpu;
    }
    public LineChart getCpu1() {
        return cpu1;
    }
    public LineChart getCpu2() {
        return cpu2;
    }

    public void cleanCpuChartFromTo(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        cpuChart.clearFromTo(LocalTime.parse(from, formatter), LocalTime.parse(to, formatter));
        cpu = cpuChart.getChart();
        cpu1 = cpuChart.getChart();
        cpu2 = cpuChart.getChart();
    }

    private void periodUpdates() {
        Thread.startVirtualThread(() -> {
            while (true) {
                try {
                    fileStore = fileStoreChart.getChart();
                    cpuChart.addPerformance();
                    cpu = cpuChart.getChart();
                    cpu1 = cpuChart.getChart();
                    cpu2 = cpuChart.getChart();

                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
