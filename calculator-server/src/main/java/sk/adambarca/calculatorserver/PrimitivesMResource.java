package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

@MResource
public class PrimitivesMResource {

    public byte byteAddOne(byte _byte) {
        return ++_byte;
    }

    public short shortAddOne(short _short) {
        return ++_short;
    }

    public int intAddOne(int _int) {
        return ++_int;
    }

    public long longAddOne(long _long) {
        return ++_long;
    }

    public float floatAddOne(float _float) {
        return ++_float;
    }

    public double doubleAddOne(double _double) {
        return ++_double;
    }
}
