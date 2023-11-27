package sk.adambarca.managementframework.impl;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Component;
import sk.adambarca.managementframework.resource.MResource;
import sk.adambarca.managementframework.resource.Resource;
import sk.adambarca.managementframework.resource.ResourceMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Component
final class AnnotationsScannerComponent {

    private static final Logger LOGGER = Logger.getLogger(AnnotationsScannerComponent.class.getName());

    private final ResourceMapper resourceMapper = new ResourceMapper();
    private final Reflections reflections = new Reflections(
            new ConfigurationBuilder()
                    .forPackage("")
    );
    private final List<Object> classesList = reflections.get(Scanners.TypesAnnotated.with(MResource.class).asClass()).stream()
            .map(it -> {
                try {
                    return Class.forName(it.getName()).getConstructor().newInstance();
                } catch (Exception e) {
                    LOGGER.severe(e.fillInStackTrace().getMessage());
                    return "";
                }
            })
            .filter(it -> !(it instanceof String))
            .toList();
    private final List<Resource> resourceList = classesList.stream()
            .map(resourceMapper::mapToResource)
            .filter(Objects::nonNull)
            .toList();

    AnnotationsScannerComponent() {
        resourceList.forEach(it -> LOGGER.info("Created Resource: \n" + it));
    }

    List<Resource> getResourceList() {
        return resourceList;
    }

    Optional<Resource> findResourceByType(String type) {
        return resourceList.stream().filter(r -> r.type().equals(type)).findFirst();
    }

    Optional<Object> getClassByClassType(String classType) {
        return classesList.stream()
                .filter(resource -> resource.getClass().getSimpleName().equals(classType))
                .findFirst();
    }
}
