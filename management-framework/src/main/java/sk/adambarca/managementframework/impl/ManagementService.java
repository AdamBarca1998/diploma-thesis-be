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
public class ManagementService {

    private final TypeConversionFactory typeConversionFactory = new TypeConversionFactory();
    private final AnnotationsScannerComponent annotationsScanner;
    private final PropertyMapper propertyMapper = new PropertyMapper();

    ManagementService(AnnotationsScannerComponent annotationsScanner) {
        this.annotationsScanner = annotationsScanner;
    }

    List<Resource> getResourceList() {
        return annotationsScanner.getResourceList();
    }

    Optional<Resource> findResourceByType(String type) {
        return annotationsScanner.findResourceByType(type);
    }

    Info getInfoByType(String type) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(type);

        Object[] enumConstants = clazz.getEnumConstants();
        List<String> enumValues = enumConstants == null ? List.of() : Arrays.stream(enumConstants)
                .map(Object::toString)
                .toList();

        return new Info(type, propertyMapper.mapToProperties(clazz), enumValues);
    }

    public Object callFunction(String classType, String functionName, Optional<Map<String, Object>> params)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        final Object clazz = annotationsScanner.getClassByClassType(classType)
                .orElseThrow(() -> new ClassNotFoundException(STR."Class '\{classType}' not found!"));
        final Method method = Arrays.stream(clazz.getClass().getMethods())
                .filter(m -> m.getName().equals(functionName))
                .findFirst()
                .orElseThrow(() -> new NoSuchMethodException(STR."Method '\{functionName}' not found!"));

        return invokeMethodWithParams(clazz, method, params);
    }

    private Object invokeMethodWithParams(Object clazz, Method method, Optional<Map<String, Object>> params) throws InvocationTargetException, IllegalAccessException {
        final var parameters = method.getParameters();
        final var requiredCount = Arrays.stream(parameters)
                .filter(this::isNotOptional)
                .count();
        final var paramsSize = params.map(Map::size).orElse(0);

        if (requiredCount > paramsSize) {
            throw new NotCorrectNumberOfPropertiesException(
                    STR."Method '\{method.getName()}' has \{requiredCount} required properties, not \{paramsSize}!"
            );
        }

        return method.invoke(clazz, convertParams(parameters, params).toArray());
    }

    private List<Object> convertParams(Parameter[] parameters, Optional<Map<String, Object>> params) {
        final List<Object> userParameters = new ArrayList<>();
        final JsonNode jsonNode = new ObjectMapper().valueToTree(params.orElse(null));

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
