### String

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is not of type String!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for String is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<String>."

**Example:**

Class:
```java
@MResource
public class BasicClassesMResource {

    public String sayHello(String s) {
      return STR."Hello \{s}!";
    }
}
```

Url:
```
[server]:[port]/management/BasicClassesMResource/sayHello
```

Body:
```json 
{
  "s": "World"
}
```

Return:
```
Hello World!
```
----------------------------------

### Enum
Enums do not allow the direct use of their integer representation.

**Errors:**

* NotValidTypeException
  * Status Code: 406
  * Message: "The \{ value } is not of type Enum!"
* NotValidTypeException
  * Status Code: 406
  * Message: "Using 'null' directly for Enum is not allowed.
    If you intend to represent an optional value, it's recommended to use the Optional wrapper.
    For example, you can use Optional\<Enum>."

**Example:**

Class:
```java
public enum Operator {
    ADD, // 0
    SUB // 1
}

@MResource
public class BasicClassesMResource {

    public double addOneByEnum(int number, Operator operator) {
      return switch (operator) {
        case ADD -> number + 1;
        case SUB -> number - 1;
        default -> throw new RuntimeException("Unknown operator " + operator);
      };
    }
}
```

Url:
```
[server]:[port]/management/BasicClassesMResource/addOneByEnum
```

Body:
```json 
{
  "number": 0,
  "operator": "ADD"
}
```

Return:
```
1.0
```
----------------------------------

### Optional //TODO
If you want to create optional arguments, you have to wrap the argument in the **class Optional**.

**Example:**
```java
@MResource
public class CalculatorMResource {

    public double sumAll(
            Optional<Double> optionalVariable, 
            Double notOptionalVariable
    ) {
        return optionalVariable.orElse(0.0) + notOptiovalVariable;
    }
}
```

**Errors:**
* NotCorrectNumberOfProperties
    * Status Code: 406
    * Message: Method '\{method.getName()}' has \{requiredCount} required properties, not \{params.size()}

* NotOptionalProperty
    * Status Code: 406
    * Message: Property '\{parameter.getName()}' can't have null value!

### Record (POJO)
For create POJO in Java use ```Record```! Classic remapping from JSON to Object.

**Example:**
```java
public record Argument(
        Double value,
        List<Argument> list
) {
}
```

```java
@MResource
public class CalculatorMResource {
    
  public double sumArgumentMProperty(Argument args) {
    // ..
  }
}
```

Syntax:
```json
{
  "args": {
    "value": 1.0,
    "list": [
      {
        "value": 2.0,
        "list": []
      }
    ]
  }
}
```

**Errors:**
* ConversionException
    * Status Code: 406
    * Message: Error converting JSON to type '\{type.getTypeName()}' \n \{e.getMessage()}