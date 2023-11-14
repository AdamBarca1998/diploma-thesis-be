# Management Framework

## Property
Properties are arguments in methods, and fields in classes. Return type in the method **is not property**.

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

### Specific for arguments
Things that are specific only to arguments.

#### optional
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