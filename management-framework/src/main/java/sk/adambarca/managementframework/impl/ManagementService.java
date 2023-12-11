package sk.adambarca.managementframework.impl;

import org.springframework.stereotype.Service;
import sk.adambarca.managementframework.impl.typeconverter.TypeConversionFactory;
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
public final class ManagementService {

    private final TypeConversionFactory typeConversionFactory = new TypeConversionFactory();
    private final AnnotationsScannerComponent annotationsScanner;

    ManagementService(AnnotationsScannerComponent annotationsScanner) {
        this.annotationsScanner = annotationsScanner;
    }

    List<Resource> getResourceList() {
        return annotationsScanner.getResourceList();
    }

    Optional<Resource> findResourceByType(String type) {
        return annotationsScanner.findResourceByType(type);
    }

    Optional<Object> callFunction(String classType, String functionName, Map<String, Object> params)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        final Object clazz = annotationsScanner.getClassByClassType(classType)
                .orElseThrow(() -> new ClassNotFoundException("Class '" + classType + "' not found!"));
        final Method method = Arrays.stream(clazz.getClass().getMethods())
                .filter(m -> m.getName().equals(functionName))
                .findFirst()
                .orElseThrow(() -> new NoSuchMethodException("Method '" + functionName + "' not found!"));

        return Optional.ofNullable(invokeMethodWithParams(clazz, method, params));
    }

    private Object invokeMethodWithParams(Object clazz, Method method, Map<String, Object> params) throws InvocationTargetException, IllegalAccessException {
        final var parameters = method.getParameters();
        final var requiredCount = Arrays.stream(parameters)
                .filter(this::isNotOptional)
                .count();

        if (requiredCount > params.size()) {
            throw new NotCorrectNumberOfPropertiesException(
                    STR."Method '\{method.getName()}' has \{requiredCount} required properties, not \{params.size()}"
            );
        }

        return method.invoke(clazz, convertParams(parameters, params).toArray());
    }

    private List<Object> convertParams(Parameter[] parameters, Map<String, Object> params) {
        List<Object> userParameters = new ArrayList<>();

        Arrays.stream(parameters).forEach(parameter -> {
            final var value = params.get(parameter.getName());
            if (value == null && isNotOptional(parameter)) {
                throw new NotOptionalPropertyException(STR."Property '\{parameter.getName()}' can't have null value!");
            }

            final var conversionStrategy = typeConversionFactory.getStrategy(parameter.getParameterizedType());

            userParameters.add(conversionStrategy.convert(value == null ? null : value.toString()));
        });

        return userParameters;
    }

    private boolean isNotOptional(Parameter parameter) {
        return !parameter.getType().isAssignableFrom(Optional.class);
    }
}
