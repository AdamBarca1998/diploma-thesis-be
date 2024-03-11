# Management Framework

TODO

----------------------------------------------------------------------------

## Resource

Resource is a class with annotation ```@MResource```.

----------------------------------------------------

**Example:**

```java
@MResource(
        name = "Calculator ABC",
        description = "Calculator description",
        icon = "fa-solid fa-paper-plane"
)
public final class Calculator {
    // ...
}
```

**Attributes:**

* name - default class simple type ```class.getSimpleName()```
* description - default empty string ```""```
* icon - from library FontAwesome https://fontawesome.com/. Default empty string ```""```

## Property (Field)

Properties are fields in classes. Syntax for getter and setter is Java syntax **set[Name] get[Name]**.

--------------------------------------------------

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

## Property (Parameter)

Properties are parameters in methods. Return type in the method **is not property**.
The Framework supports nested types, so you can have a list into a list.

--------------------------------------

### Data types
Data types that the framework allows used in properties.

**Example:**
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

### Supports all Java21 primitives and their wrappers

- [Primitives](docs/readme/primitives.md)

* Byte & byte
* Short & short
* Integer & int
* Long & long
* Float & float
* Double & double
* Character & char
* Boolean & boolean

### Support basic classes:

- [Basic classes](docs/readme/basic-classes.md)

* String
* Enum
* Optional
* Record

### Data structures:

- [Data structures](docs/readme/data-structures.md)

* List
* Set
* Map

## Tests:

Test's tables for framework:

--------------------------------------

### Numeric types

| Test                                | Double         | Float          | Short  | Byte          | Int           | Long          | Char         |
|-------------------------------------|----------------|----------------|--------|---------------|---------------|---------------|--------------|
| Positive & Negative (Validity Type) | ✔              | ✔              | ✔      | ✔             | ✔             | ✔             | ✔            |
| Integer                             | ✔              | ✔              | Validity Type      | Validity Type             | Validity Type             | Validity Type             | ✔            |
| Integer Part                        | ✔              | ✔              | ✔      | ✔             | ✔             | ✔             | ✔            |
| Min                                 | ✔              | ✔              | ✔      | ✔             | ✔             | ✔             | ✔            |
| Max                                 | ✔              | ✔              | ✔      | ✔             | ✔             | ✔             | ✔            |
| 0                                   | ✔              | ✔              | ✔      | ✔             | ✔             | ✔             | ✔            |
| Decimal Part                        | Validity Type  | Validity Type  | ❌      | ❌             | ❌            | ❌             | ❌            |
| Invalidity Type                     | ❌             | ❌              | ❌      | ❌             | ❌            | ❌             | ❌            |
| On Null                             | ❌             | ❌              | ❌      | ❌             | ❌            | ❌             | ❌            |
| Overflow                            | ❌             | ❌              | ❌      | ❌             | ❌            | ❌             | ❌            |
| Underflow                           | ❌             | ❌              | ❌      | ❌             | ❌            | ❌             | ❌            |
| Empty                               |                |                |        |               |               |               | ✔            |
| Special                             |                |                |        |               |               |               | ✔            |

### Nested types
| Test            | List | Map  | Set  | Record        |
|-----------------|------|------|------|---------------|
| Validity Type   | ✔    | ✔    | ✔    | ✔             |
| Empty           | ✔    | ✔    | ✔    | ✔ ❌ (2 types) |
| Nested          | ✔    | ✔    | ✔    | ✔             |
| Invalidity Type | ❌    | ❌    | ❌    | ❌             |
| Null Validity   | ❌    | ❌    | ❌    | ❌             |
| On Null         | ❌    | ❌    | ❌    | ❌             |
| Different Types | ❌    | ❌    | ❌    | ❌             |
