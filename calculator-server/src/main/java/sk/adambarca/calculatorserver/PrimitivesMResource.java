package sk.adambarca.calculatorserver;

import sk.adambarca.managementframework.resource.MResource;

import static java.lang.StringTemplate.STR;

@MResource
public class PrimitivesMResource {

    public byte byteAdd(byte bytePrim, Byte byteWrap) {
        return (byte) (bytePrim + byteWrap);
    }

    public short shortAdd(short shortPrim, Short shortWrap) {
        return (short) (shortPrim + shortWrap);
    }

    public int intAdd(int intPrim, Integer intWrap) {
        return intPrim + intWrap;
    }

    public long longAdd(long longPrim, Long longWrap) {
        return longPrim + longWrap;
    }

    public float floatAdd(float floatPrim, Float floatWrap) {
        return floatPrim + floatWrap;
    }

    public double doubleAdd(double doublePrim, Double doubleWrap) {
        return doublePrim + doubleWrap;
    }

    public String charConcat(char charPrim, Character charWrap) {
        return STR."\{charPrim}\{charWrap}";
    }

    public boolean boolAnd(boolean boolPrim, Boolean boolWrap) {
        return boolPrim && boolWrap;
    }
}
