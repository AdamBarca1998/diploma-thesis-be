### Byte & byte

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is not of type Byte or byte!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is out of range for byte!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Byte or byte is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<Byte>."

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

### Short and short

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is not of type Short or short!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is out of range for Short or short!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Short or short is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<Short>."

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

### Integer and int

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is not type of Integer or int!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is out of range for Integer or int!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Integer or int is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<Integer>."

**Example:**

Class:
```java
@MResource
public class PrimitivesMResource {

  public int intAddOne(int _int) {
    return ++_int;
  }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/intAddOne
```

Body:
```json 
{
  "_int": 1
}
```
----------------------------------

### Long and long

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is not type of Long or long!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is out of range for Long or long!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Long or long is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<Long>."

**Example:**

Class:
```java
@MResource
public class PrimitivesMResource {

  public long longAddOne(long _long) {
    return ++_long;
  }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/longAddOne
```

Body:
```json 
{
  "_long": 1
}
```
----------------------------------

### Float and float

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is not type of Float or float!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is out of range for Float or float!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Float or float is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<Float>."

**Example:**

Class:
```java
@MResource
public class PrimitivesMResource {

  public long floatAddOne(long _float) {
    return ++_float;
  }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/floatAddOne
```

Body:
```json 
{
  "_float": 0.5
}
```
----------------------------------

### Double and double

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is not type of Double or double!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ _value } is out of range for Double or double!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Double or double is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<Double>."

**Example:**

Class:
```java
@MResource
public class PrimitivesMResource {

  public double doubleAddOne(double _double) {
    return ++_double;
  }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/doubleAddOne
```

Body:
```json 
{
  "_double": 0.5
}
```