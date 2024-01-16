package sk.adambarca.managementframework.supportclasses;

import sk.adambarca.managementframework.resource.MResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@MResource
public class DataStructuresMResource {

    public List<Double> addZero(List<Double> numbers) {
        final var list = new ArrayList<>(numbers);
        list.add(0.0);

        return list;
    }

    public int sumNestedLists(List<List<Integer>> nestedList) {
        return nestedList.stream()
                .flatMap(List::stream)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int sumSets(Set<Optional<Integer>> aSet, Set<Integer> bSet) {
        return aSet.stream().mapToInt(e -> e.orElse(0)).sum() + bSet.stream().mapToInt(e -> e).sum();
    }

    public long getCountSets(Set<Set<Integer>> nestedSet) {
        return nestedSet.stream()
                .flatMap(Set::stream)
                .mapToInt(Integer::intValue)
                .count();
    }

    public Set<String> getKeys(Map<String, Integer> map) {
        return map.keySet();
    }

    public List<String> getValuesFromNestedMap(Map<String, Map<Integer, String>> nestedMap) {
        return nestedMap.values()
                .stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .toList();
    }
}
