### String

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

**Errors:**

* NotValidTypeException
  * Status Code: 406
  * Message: "The \{ value } is not of type String!"
* NotValidTypeException
  * Status Code: 406
  * Message: "Using 'null' directly for String is not allowed.
    If you intend to represent an optional value, it's recommended to use the Optional wrapper.
    For example, you can use Optional\<String>."
----------------------------------

### Enum
Enums do not allow the direct use of their integer representation.

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

**Errors:**

* NotValidTypeException
  * Status Code: 406
  * Message: "The \{ value } is not of type Enum!"
* NotValidTypeException
  * Status Code: 406
  * Message: "Using 'null' directly for Enum is not allowed.
    If you intend to represent an optional value, it's recommended to use the Optional wrapper.
    For example, you can use Optional\<Enum>."
----------------------------------

### Optional
If you intend to have optional arguments, you can achieve this by wrapping the argument in the **Optional** class.

**Example:**
```java
@MResource
public class BasicClassesMResource {

  public double sumOptional(double num1, Optional<Double> num2) {
    return num1 + num2.orElse(0.0);
  }
}
```

Url:
```
[server]:[port]/management/BasicClassesMResource/sumOptional
```

Body:
```json 
{
  "num1": 0.5
}
```

Return:
```
0.5
```

**Errors:**
* NotCorrectNumberOfProperties
    * Status Code: 406
    * Message: Method '\{method.getName()}' has \{requiredCount} required properties, not \{params.size()}

* NotOptionalProperty
    * Status Code: 406
    * Message: Property '\{parameter.getName()}' can't have null value!
----------------------------------

### Record (POJO)
To create POJOs in Java, use ```Record```! It offers a classic way to map JSON to objects.

**Example:**
```java
public record Person(
        String name,
        int age,
        Optional<Person> child
) {
}

@MResource
public class BasicClassesMResource {

  public int sumAges(Person person) {
    return person.age() + (person.child().isPresent() ? sumAges(person.child().get()) : 0);
  }
}
```

Url:
```
[server]:[port]/management/BasicClassesMResource/sumAges
```

Body:
```json 
{
  "person": {
    "name": "Adam",
    "age": 20,
    "child": {
      "name": "John",
      "age": 5,
      "child": null
    }
  }
}
```

Return:
```
25
```

**Errors:**
* ConversionException
  * Status Code: 406
  * Message: Error converting JSON to type '\{type.getTypeName()}' \n The Property \{field.getName()} has error: \{e}}
* ConversionException
    * Status Code: 406
    * Message: Error converting JSON to type '\{type.getTypeName()}' \n \{e.getMessage()}