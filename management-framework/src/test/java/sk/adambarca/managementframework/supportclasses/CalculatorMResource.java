package sk.adambarca.managementframework.supportclasses;

import sk.adambarca.managementframework.resource.MResource;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@MResource
public final class CalculatorMResource {

    public double sumMapInteger(Map<Integer, List<Integer>> map) {
        return map.values().stream()
                .map(list -> list.stream().mapToInt(Integer::intValue).sum())
                .mapToInt(Integer::intValue)
                .sum();
    }

    public boolean isSupportType(Calendar calendar) {
        return false;
    }
}
