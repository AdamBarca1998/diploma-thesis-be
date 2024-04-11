package sk.adambarca.calculatorserver.resources;

import sk.adambarca.calculatorserver.resources.charts.*;
import sk.adambarca.managementframework.resource.MResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@MResource(
        name = "Charts example",
        periodTimeMs = 60 * 1000
)
public class MemoryMResource {

    private PieChart fileStore;
    private LineChart cpu;
    private final CpuChart cpuChart = new CpuChart();
    private final FileStoreChart fileStoreChart = new FileStoreChart();

    public MemoryMResource() {
        periodUpdates();
    }

    public PieChart getFileStore() {
        return fileStore;
    }

    public LineChart getCpu() {
        return cpu;
    }

    public void cleanCpuChart() {
        cpuChart.clear();
        cpu = cpuChart.getChart();
    }

    private void periodUpdates() {
        Thread.startVirtualThread(() -> {
            while (true) {
                try {
                    fileStore = fileStoreChart.getChart();
                    cpu = cpuChart.getChart();

                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
