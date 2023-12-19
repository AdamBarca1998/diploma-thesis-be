# Management Framework

## Resource
Resource is a class with annotation ```@MResource```.

```java
@MResource(
        name = "Calculator ABC",
        description = "Calculator description",
        icon = "Calculator icon"
)
public final class Calculator {
    // ...
}
```

### Attributes
* name - default class simple type ```class.getSimpleName()```
* description - default empty string ```""```
* icon - default empty string ```""```

## Property (Field)
Properties are fields in classes. Syntax for getter and setter is Java syntax **set[Name] get[Name]**.

They are divided into three types:
### Hide
Hide property doesn't have any getter or setter.

```java
@MResource
public class MemoryMResource {

    private String secretKey = "abcd";
}
```

### Disable
Disable property doesn't have any setter.

```java
@MResource
public class MemoryMResource {

    private String id = 0;

    public int getId() {
        return id;
    }
}
```

### Enable
Enable property **has to** have one getter and setter.

```java
@MResource
public class MemoryMResource {

    private Double value = null;
    
    public Double getValue() {
      return value;
    }
    
    public void setValue(Double value) {
      this.value = value;
    }
}
```

## Property (Argument)
Properties are arguments in methods. Return type in the method **is not property**.
The Framework supports nested types, so you can have a list into a list.

### Data types
Data types that the framework allows used in properties.

```java
@MResource
public class CalculatorMResource {

    public double sumAll(
            int a, // allowed type
            double b, // allowed type
            SuperFileTypeXYZ c // not allowed type
    ) {
        return a + b + c;
    }
}
```


### supports all Java21 primitive data types:
* byte 
* short
* int
* long
* float
* double
* char
* boolean

### supports primitive wrapper classes:
* Byte
* Short
* Integer
* Long
* Float
* Double
* Character
* Boolean

### support basic classes:
* String

### data structures:
* List
* Set - syntax is same than **List**
* Map - syntax for ```Map<String, List<Integer>> someMapName```
```json 
{
    "someMapName": {
        "key1": [1],
        "key2": [1, 2],
        "key3": []
    }
}
```

### optional
If you want to create optional arguments, you have to wrap the argument in the **class Optional**.

Example:
```java
@MResource
public class CalculatorMResource {

    public double sumAll(
            Optional<Double> optionalVariable, 
            Double notOptiovalVariable
    ) {
        return optionalVariable.orElse(0.0) + notOptiovalVariable;
    }
}
```

Errors:
* NotCorrectNumberOfProperties
  * Status Code: 406
  * Message: Method '\{method.getName()}' has \{requiredCount} required properties, not \{params.size()}

* NotOptionalProperty
    * Status Code: 406
    * Message: Property '\{parameter.getName()}' can't have null value!