# Management Framework

## Property
Properties are arguments in methods. Return type in the method **is not property**.

### Data types
Data types that the framework allows used in properties.

Example:
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
  * every element must be split with **','** example: **numbers=1,2,3**
  * for empty string you write only name example: **numbers=**

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
  * Status Code: 400
  * Message: Method '\{method.getName()}' has \{requiredCount} required properties, not \{params.size()}

* NotOptionalProperty
    * Status Code: 400
    * Message: Property '\{parameter.getName()}' can't have null value!