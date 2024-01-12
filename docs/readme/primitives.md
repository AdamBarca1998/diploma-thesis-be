### Byte & byte

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is not of type Byte or byte!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is out of range for byte!"
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

    public byte byteAdd(byte bytePrim, Byte byteWrap) {
        return (byte) (bytePrim + byteWrap);
    }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/byteAdd
```

Body:
```json 
{
  "bytePrim": 0,
  "byteWrap": 1
}
```

Return:
```
1
```
----------------------------------

### Short and short

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is not of type Short or short!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is out of range for Short or short!"
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

    public short shortAdd(short shortPrim, Short shortWrap) {
        return (short) (shortPrim + shortWrap);
    }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/shortAdd
```

Body:
```json 
{
  "shortPrim": 0,
  "shortWrap": 1
}
```

Return:
```
1
```
----------------------------------

### Integer and int

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is not type of Integer or int!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is out of range for Integer or int!"
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

    public int intAdd(int intPrim, Integer intWrap) {
        return intPrim + intWrap;
    }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/intAdd
```

Body:
```json 
{
  "intPrim": 0,
  "intWrap": 1
}
```

Return:
```
1
```
----------------------------------

### Long and long

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is not type of Long or long!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is out of range for Long or long!"
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

    public long longAdd(long longPrim, Long longWrap) {
        return longPrim + longWrap;
    }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/longAdd
```

Body:
```json 
{
  "longPrim": 0,
  "longWrap": 1
}
```

Return:
```
1
```
----------------------------------

### Float and float

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is not type of Float or float!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is out of range for Float or float!"
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

    public float floatAdd(float floatPrim, Float floatWrap) {
        return floatPrim + floatWrap;
    }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/floatAdd
```

Body:
```json 
{
  "floatPrim": 0.5,
  "floatWrap": 1
}
```

Return:
```
1.5
```
----------------------------------

### Double and double

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is not type of Double or double!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is out of range for Double or double!"
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

    public double doubleAdd(double doublePrim, Double doubleWrap) {
        return doublePrim + doubleWrap;
    }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/doubleAdd
```

Body:
```json 
{
  "doublePrim": 0.5,
  "doubleWrap": 1
}
```

Return:
```
1.5
```
----------------------------------

### Character and char

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is not type of Character or char!"
* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is out of range for Character or char!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Character or char is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<Character>."

**Example:**

Class:
```java
@MResource
public class PrimitivesMResource {

    public String charConcat(char charPrim, Character charWrap) {
        return STR."\{charPrim}\{charWrap}";
    }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/charConcat
```

Body:
```json 
{
  "charPrim": "A",
  "charWrap": "B"
}
```

Return:
```
AB
```
----------------------------------

### Boolean and boolean

**Errors:**

* NotValidTypeException
    * Status Code: 406
    * Message: "The \{ value } is not type of Boolean or boolean!"
* NotValidTypeException
    * Status Code: 406
    * Message: "Using 'null' directly for Boolean or boolean is not allowed.
      If you intend to represent an optional value, it's recommended to use the Optional wrapper.
      For example, you can use Optional\<Boolean>."

**Example:**

Class:
```java
@MResource
public class PrimitivesMResource {

    public boolean boolAnd(boolean boolPrim, Boolean boolWrap) {
        return boolPrim && boolWrap;
    }
}
```

Url:
```
[server]:[port]/management/PrimitivesMResource/boolAnd
```

Body:
```json 
{
  "boolPrim": true,
  "boolWrap": false
}
```

Return:
```
false
```