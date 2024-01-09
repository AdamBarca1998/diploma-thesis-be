### Supports all Java21 primitives and their wrappers
The framework supports all Java 21 primitives and their corresponding wrapper classes.

------------------------------------------------

### byte and Byte

**Errors:**
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is not type Byte or byte!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is out of range for byte!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Byte or byte is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional<Byte>."

**Example:**

Class:
```java
@MResource
public class PrimitivesMResource {

  public byte byteAddOne(byte _byte) {
    return ++_byte;
  }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/byteAddOne
```

Body:
```json 
{
  "_byte": 1
}
```
----------------------------------

### short and Short

**Errors:**
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is not type Short or short!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is out of range for Short or short!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Short or short is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional<Byte>."

**Example:**

Class:
```java
@MResource
public class PrimitivesMResource {

  public short shortAddOne(short _short) {
    return ++_short;
  }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/shortAddOne
```

Body:
```json 
{
  "_short": 1
}
```
----------------------------------

### int and Integer //TODO:ASDsadsadsada

**Errors:**
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is not type Short or short!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is out of range for Short or short!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Short or short is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional<Byte>."

**Example:**

Class:
```java
@MResource
public class PrimitivesMResource {

  public short intAddOne(int _short) {
    return ++_short;
  }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/shortAddOne
```

Body:
```json 
{
  "_short": 1
}
```
----------------------------------

* long
* float
* double
* char
* boolean

### Supports primitive wrapper classes:
* Byte
* Short
* Integer
* Long
* Float
* Double
* Character
* Boolean