package sk.adambarca.managementframework.resource;

import org.reflections.ReflectionUtils;
import org.reflections.util.ReflectionUtilsPredicates;
import sk.adambarca.managementframework.function.Function;
import sk.adambarca.managementframework.function.FunctionMapper;
import sk.adambarca.managementframework.property.Property;
import sk.adambarca.managementframework.property.PropertyMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class ResourceMapper {

    private static final Logger LOGGER = Logger.getLogger(ResourceMapper.class.getName());

    private final PropertyMapper propertyMapper = new PropertyMapper();
    private final FunctionMapper functionMapper = new FunctionMapper();

    public Resource mapToResource(Object mObject) {
        final var clazz = mObject.getClass();
        final var publicPredicate = ReflectionUtilsPredicates.withModifier(Modifier.PUBLIC);
        final var methods = ReflectionUtils.getAllMethods(clazz, publicPredicate).stream()
                .sorted(Comparator.comparing(Method::getName))
                .toList();

        if (isDuplicityMethods(methods)) {
            LOGGER.severe("Class '" + mObject.getClass().getName() + "' has duplicity method names!");
            return null;
        }
        // build
        final var resourceBuilder = new Resource.Builder();
        final var properties = getProperties(mObject);

        resourceBuilder.type(clazz.getSimpleName());
        resourceBuilder.properties(properties);

        Arrays.stream(clazz.getAnnotations())
                .forEach(annotation -> {
                    if (annotation instanceof MResource ann) {
                        resourceBuilder.name(ann.name().isEmpty() ? clazz.getSimpleName() : ann.name());
                        resourceBuilder.description(ann.description());
                        resourceBuilder.icon(ann.icon());
                    }
                })
        ;

        resourceBuilder.functions(getFunctions(properties, methods));
        resourceBuilder.validations(List.of());

        return resourceBuilder.build();
    }

    private boolean isDuplicityMethods(List<Method> methods) {
        final var noDuplicityCount = methods.stream()
                .map(Method::getName)
                .distinct()
                .count();

        return methods.size() != noDuplicityCount;
    }

    private List<Property> getProperties(Object mObject) {
        final Predicate<Field> privatePredicate = ReflectionUtilsPredicates.withModifier(Modifier.PRIVATE);

        return ReflectionUtils.getAllFields(mObject.getClass(), privatePredicate).stream()
                .map(field -> propertyMapper.mapToProperty(mObject, field))
                .filter(Objects::nonNull)
                .toList();
    }

    private List<Function> getFunctions(List<Property> properties, List<Method> methods) {
        return methods.stream()
                .filter(method -> isNotSetterOrGetter(properties, method))
                .map(functionMapper::mapToFunction)
                .toList();
    }

    private boolean isNotSetterOrGetter(List<Property> properties, Method method) {
        String methodName = method.getName();

        if (isGetter(methodName) || isSetter(methodName)) {
            String propertyName = extractPropertyName(methodName);

            return properties.stream().noneMatch(property -> property.name().equals(propertyName));
        }

        return true;
    }

    private boolean isGetter(String methodName) {
        return methodName.startsWith("get") && methodName.length() > 3;
    }

    private boolean isSetter(String methodName) {
        return methodName.startsWith("set") && methodName.length() > 3;
    }

    private String extractPropertyName(String methodName) {
        return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
    }
}
