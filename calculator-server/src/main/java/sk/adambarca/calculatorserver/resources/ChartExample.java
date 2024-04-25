package sk.adambarca.calculatorserver.resources;

import sk.adambarca.calculatorserver.resources.charts.*;
import sk.adambarca.managementframework.resource.MResource;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@MResource(
        name = "Charts example",
        periodTimeMs = 60 * 1000
)
public class ChartExample {

    private PieChart fileStore;
    private LineChart cpu;
    private final CpuChart cpuChart = new CpuChart();
    private final FileStoreChart fileStoreChart = new FileStoreChart();

    public ChartExample() {
        periodUpdates();
    }

    public PieChart getFileStore() {
        return fileStore;
    }

    public LineChart getCpu() {
        return cpu;
    }

    public void cleanCpuChartFromTo(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        cpuChart.clearFromTo(LocalTime.parse(from, formatter), LocalTime.parse(to, formatter));
        cpu = cpuChart.getChart();
    }

    private void periodUpdates() {
        Thread.startVirtualThread(() -> {
            while (true) {
                try {
                    fileStore = fileStoreChart.getChart();
                    cpuChart.addPerformance();
                    cpu = cpuChart.getChart();

                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
