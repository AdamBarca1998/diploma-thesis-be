package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

import java.util.List;
import java.util.Map;

@MResource(
        name = "Calculator",
        description = "Calculator description",
        icon = "Calculator icon"
)
public final class CalculatorMResource {

    private Double memory = null;

    public double sumMap(Map<String, List<Integer>> map) {
        return map.values().stream()
                .map(list -> list.stream().mapToInt(Integer::intValue).sum())
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Double getMemory() {
        return memory;
    }

    public void setMemory(Double memory) {
        this.memory = memory;
    }
}
