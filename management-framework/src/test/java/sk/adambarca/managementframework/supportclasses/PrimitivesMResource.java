package sk.adambarca.managementframework.supportclasses;

import sk.adambarca.managementframework.resource.MResource;

@MResource
public class PrimitivesMResource {

    public byte byteAddOne(byte _byte) {
        return ++_byte;
    }
}
