package sk.adambarca.calculatorserver.resources.charts;

import sk.adambarca.calculatorserver.resources.charts.structure.DataSet;
import sk.adambarca.calculatorserver.resources.charts.structure.PieChart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileStoreChart {

    private final static String PATH = "C:/";

    public PieChart getChart() {
        try {
            final var fileStore = Files.getFileStore(Path.of(PATH));

            final long total = fileStore.getTotalSpace();
            final long free = fileStore.getUnallocatedSpace();
            final long used = total - free;

            final var dataSet = new DataSet(
                    "File Store",
                    List.of(convertToGiB(free), convertToGiB(used)),
                    List.of("rgb(255, 99, 132)", "rgb(54, 162, 235)")
            );

            return new PieChart(List.of("Free", "Used"), List.of(dataSet));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double convertToGiB(long n) {
        return (double) n / (1024 * 1024 * 1024);
    }
}
