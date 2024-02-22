package sk.adambarca.managementframework.impl;

import sk.adambarca.managementframework.property.Property;

import java.util.List;

record Info(
        String type,
        List<Property> properties,
        List<String> enums
) {
}
