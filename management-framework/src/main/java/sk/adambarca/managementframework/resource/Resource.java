package sk.adambarca.managementframework.resource;

import sk.adambarca.managementframework.function.Function;
import sk.adambarca.managementframework.property.Property;

import java.util.List;

public record Resource(
        String name,
        String description,
        String icon,
        int periodTimeMs,
        String type,
        List<Property> properties,
        List<Function> functions,
        List<String> validations
) {

    public static final class Builder {

        private String name;
        private String description;
        private String icon;
        private int periodTimeMs;
        private String type;
        private List<Property> properties;
        private List<Function> functions;
        private List<String> validations;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder periodTimeMs(int periodTimeMs) {
            this.periodTimeMs = periodTimeMs;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder properties(List<Property> properties) {
            this.properties = properties;
            return this;
        }

        public Builder functions(List<Function> functions) {
            this.functions = functions;
            return this;
        }

        public Builder validations(List<String> validations) {
            this.validations = validations;
            return this;
        }

        public Resource build() {
            return new Resource(name, description, icon, periodTimeMs, type, properties, functions, validations);
        }
    }
}
