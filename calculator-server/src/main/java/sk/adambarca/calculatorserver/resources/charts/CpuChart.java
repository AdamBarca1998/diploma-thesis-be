package sk.adambarca.calculatorserver.resources.charts;


import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CpuChart {

    private final List<CpuPerformance> cpuPerformanceList = new ArrayList<>();

    public LineChart getChart() {
        addPerformance();

        final var dataSet = new DataSet(
                "load",
                getAllCpuLoads(),
                List.of("rgb(255, 99, 132)"),
                List.of("rgb(255, 99, 132)"),
                0
        );

        return new LineChart(getAllDateTimes(), List.of(dataSet));
    }

    public void clear() {
        cpuPerformanceList.clear();
    }

    private void addPerformance() {
        OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        cpuPerformanceList.add(
                new CpuPerformance(
                        LocalDateTime.now(),
                        operatingSystemMXBean.getCpuLoad() * 100
                )
        );
    }

    private List<String> getAllDateTimes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return cpuPerformanceList.stream()
                .map(CpuPerformance::localDateTime)
                .map(dateTime -> dateTime.format(formatter))
                .toList();
    }

    private List<Double> getAllCpuLoads() {
        return cpuPerformanceList.stream()
                .map(CpuPerformance::cpuLoad)
                .toList();
    }

    private record CpuPerformance(
            LocalDateTime localDateTime,
            double cpuLoad
    ) {
    }
}
