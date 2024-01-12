package sk.adambarca.managementframework.supportclasses;

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
}
