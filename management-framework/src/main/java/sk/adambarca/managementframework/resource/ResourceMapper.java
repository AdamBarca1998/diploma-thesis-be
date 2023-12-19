package sk.adambarca.managementframework.resource;

import org.reflections.ReflectionUtils;
import org.reflections.util.ReflectionUtilsPredicates;
import sk.adambarca.managementframework.function.FunctionMapper;
import sk.adambarca.managementframework.property.PropertyMapper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ResourceMapper {

    private static final Logger LOGGER = Logger.getLogger(ResourceMapper.class.getName());

    private final PropertyMapper propertyMapper = new PropertyMapper();
    private final FunctionMapper functionMapper = new FunctionMapper();

    public Resource mapToResource(Object mResource) {
        final var clazz = mResource.getClass();
        final var publicPredicate = ReflectionUtilsPredicates.withModifier(Modifier.PUBLIC);
        final var methods = ReflectionUtils.getAllMethods(clazz, publicPredicate);

        if (isDuplicityMethods(methods)) {
            LOGGER.severe("Class '" + mResource.getClass().getName() + "' has duplicity method names!");
            return null;
        }
        // build
        final var resourceBuilder = new Resource.Builder();
        resourceBuilder.type(clazz.getSimpleName());

        Arrays.stream(clazz.getAnnotations())
                .forEach(annotation -> {
                    if (annotation instanceof MResource ann) {
                        resourceBuilder.name(ann.name().isEmpty() ? clazz.getSimpleName() : ann.name());
                        resourceBuilder.description(ann.description());
                        resourceBuilder.icon(ann.icon());
                    }
                })
        ;

        final var fields = ReflectionUtils.getAllFields(clazz, publicPredicate).stream()
                .map(propertyMapper::mapToProperty)
                .toList();
        final var functions = methods.stream()
                .map(functionMapper::mapToFunction)
                .toList();

        resourceBuilder.properties(fields);
        resourceBuilder.functions(functions);
        resourceBuilder.validations(List.of());

        return resourceBuilder.build();
    }

    private boolean isDuplicityMethods(Set<Method> methods) {
        final var noDuplicityCount = methods.stream()
                .map(Method::getName)
                .distinct()
                .count();

        return methods.size() != noDuplicityCount;
    }
}
