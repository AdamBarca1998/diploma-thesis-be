package sk.adambarca.managementframework.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import sk.adambarca.managementframework.impl.typeconverter.TypeConversionFactory;
import sk.adambarca.managementframework.property.PropertyMapper;
import sk.adambarca.managementframework.resource.Resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
final class ManagementService {

    private final TypeConversionFactory typeConversionFactory = new TypeConversionFactory();
    private final ResourcesScannerComponent resourcesScanner;
    private final PropertyMapper propertyMapper = new PropertyMapper();

    ManagementService(ResourcesScannerComponent resourcesScanner) {
        this.resourcesScanner = resourcesScanner;
    }

    List<Resource> getResourceList() {
        return resourcesScanner.getResourceList();
    }

    Optional<Resource> findResourceByType(String type) {
        return resourcesScanner.findResourceByType(type);
    }

    Info getInfoByType(String type) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(type);

        Object[] enumConstants = clazz.getEnumConstants();
        List<String> enumValues = enumConstants == null ? List.of() : Arrays.stream(enumConstants)
                .map(Object::toString)
                .toList();

        return new Info(type, propertyMapper.mapToProperties(clazz), enumValues);
    }

    Object callFunction(String classType, String functionName, Optional<Map<String, Object>> args)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        final Object obj = resourcesScanner.getObjectByClassType(classType)
                .orElseThrow(() -> new ClassNotFoundException(STR."Class '\{classType}' not found!"));
        final Method method = Arrays.stream(obj.getClass().getMethods())
                .filter(m -> m.getName().equals(functionName))
                .findFirst()
                .orElseThrow(() -> new NoSuchMethodException(STR."Method '\{functionName}' not found!"));

        return invokeMethodWithParams(obj, method, args);
    }

    private Object invokeMethodWithParams(Object clazz, Method method, Optional<Map<String, Object>> args) throws InvocationTargetException, IllegalAccessException {
        final var parameters = method.getParameters();
        final var requiredCount = Arrays.stream(parameters)
                .filter(this::isNotOptional)
                .count();
        final var paramsSize = args.map(Map::size).orElse(0);

        if (requiredCount > paramsSize) {
            throw new NotCorrectNumberOfPropertiesException(
                    STR."Method '\{method.getName()}' has \{requiredCount} required properties, not \{paramsSize}!"
            );
        }

        return method.invoke(clazz, convertParams(parameters, args).toArray());
    }

    private List<Object> convertParams(Parameter[] parameters, Optional<Map<String, Object>> args) {
        final List<Object> userParameters = new ArrayList<>();
        final JsonNode jsonNode = new ObjectMapper().valueToTree(args.orElse(null));

        Arrays.stream(parameters).forEach(parameter -> {
            final var value = jsonNode.get(parameter.getName());
            if (value == null && isNotOptional(parameter)) {
                throw new NotOptionalPropertyException(STR."Property '\{parameter.getName()}' can't have null value!");
            }

            final var conversionStrategy = typeConversionFactory.getStrategy(parameter.getType());

            userParameters.add(conversionStrategy.convert(value, parameter.getParameterizedType()));
        });

        return userParameters;
    }

    private boolean isNotOptional(Parameter parameter) {
        return !parameter.getType().isAssignableFrom(Optional.class);
    }
}
