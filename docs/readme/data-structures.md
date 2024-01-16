### List

**Example:**

Class:
```java
@MResource
public class DataStructuresMResource {

    public int sumNestedLists(List<List<Integer>> nestedList) {
        return nestedList.stream()
                .flatMap(List::stream)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
```

Url:
```
[server]:[port]/management/DataStructuresMResource/sumNestedLists
```

Body:
```json 
{
  "nestedList": [[1, 2], [-1, 5]]
}
```

Return:
```
7
```

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The value '\{ value }' is not of type List!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for List is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<List>."
----------------------------------

### Set
A Set allows the use of the same value, but it counts only one. The semantics for creating a Set are the same as for a List.

**Example:**

Class:
```java
@MResource
public class DataStructuresMResource {

    public double sumSets(Set<Optional<Integer>> aSet, Set<Integer> bSet) {
        return aSet.stream().mapToInt(e -> e.orElse(0)).sum() + bSet.stream().mapToInt(e -> e).sum();
    }
}
```

Url:
```
[server]:[port]/management/DataStructuresMResource/sumSets
```

Body:
```json 
{
  "aSet": [1, 2, null], 
  "bSet": [-1, 5, 5, 5, 5]
}
```

Return:
```
7
```

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The value '\{ value }' is not of type Set!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Set is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<List>."
----------------------------------

### Map

**Example:**

Class:
```java
@MResource
public class DataStructuresMResource {

    public Set<String> getKeys(Map<String, Integer> map) {
        return map.keySet();
    }
}
```

Url:
```
[server]:[port]/management/DataStructuresMResource/getKeys
```

Body:
```json
{
  "map": {
    "key1": 1,
    "key2": 2,
    "key3": 3
  }
}
```

Return:
```
["key1", "key2", "key3"]
```

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The value '\{ value }' is not of type Set!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Set is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<List>."