# Diploma Thesis

## Property
Properties are arguments in methods, and fields in classes. Return type in the method **is not property**.

### Data types
Data types that the framework allows used in arguments or fields.

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

### supports primitive wrapper class:
* Byte
* Short
* Integer
* Long
* Float
* Double
* Character
* Boolean


