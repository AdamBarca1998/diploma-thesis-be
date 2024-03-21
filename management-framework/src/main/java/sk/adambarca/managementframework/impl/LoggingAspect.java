package sk.adambarca.managementframework.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(public * sk.adambarca.managementframework.impl.ManagementService.*(..))")
    private void publicMethodsFromLoggingPackage() {
    }

    @Before(value = "publicMethodsFromLoggingPackage()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        logger.debug(">> {}() - {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(value = "publicMethodsFromLoggingPackage()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.debug("<< {}() - {}", methodName, result);
    }

    @AfterThrowing(pointcut = "publicMethodsFromLoggingPackage()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("<< {}() - {}", methodName, exception.getMessage());
    }
}
