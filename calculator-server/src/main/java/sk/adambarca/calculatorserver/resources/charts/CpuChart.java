package sk.adambarca.calculatorserver.resources.charts;


import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CpuChart {

    private final List<CpuPerformance> cpuPerformanceList = new ArrayList<>();

    public void clearFromTo(LocalTime from, LocalTime to) {
        cpuPerformanceList.removeIf(performance ->
                (performance.localTime.equals(from) || performance.localTime.isAfter(from)) &&
                        (performance.localTime.equals(to) || performance.localTime.isBefore(to))
        );
    }

    public LineChart getChart() {
        final var dataSet = new DataSet(
                "load",
                getAllCpuLoads(),
                List.of("rgb(255, 99, 132)"),
                List.of("rgb(255, 99, 132)"),
                0
        );

        return new LineChart(getAllDateTimes(), List.of(dataSet));
    }

    public void addPerformance() {
        OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        cpuPerformanceList.add(
                new CpuPerformance(
                        LocalTime.now(),
                        operatingSystemMXBean.getCpuLoad() * 100
                )
        );
    }

    private List<String> getAllDateTimes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return cpuPerformanceList.stream()
                .map(CpuPerformance::localTime)
                .map(dateTime -> dateTime.format(formatter))
                .toList();
    }

    private List<Double> getAllCpuLoads() {
        return cpuPerformanceList.stream()
                .map(CpuPerformance::cpuLoad)
                .toList();
    }

    private record CpuPerformance(
            LocalTime localTime,
            double cpuLoad
    ) {
    }
}
