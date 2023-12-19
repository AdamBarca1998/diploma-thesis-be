package sk.adambarca.managementframework.testclasses;

import sk.adambarca.managementframework.resource.MResource;

@MResource
public class MemoryMResource {

    private String secretKey = "secretKey";
    private Double value = null;
    private int id = 0;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }
}
