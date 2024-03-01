package sk.adambarca.managementframework.resource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for creating a resource in the Management Framework
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MResource {

    String name() default "";
    String description() default "";

    /**
     * Represent an icon from FontAwesome
     * @see https://fontawesome.com/
     */
    String icon() default "";
    int periodTimeMs() default 600000; // 10min
}
