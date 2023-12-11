package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

import java.util.List;
import java.util.Map;

@MResource
public final class CalculatorMResource {

    public double sumMap(Map<String, List<Integer>> map) {
        return map.values().stream()
                .map(list -> list.stream().mapToInt(Integer::intValue).sum())
                .mapToInt(Integer::intValue)
                .sum();
    }
}
