package sk.adambarca.managementframework.impl;

final class NotOptionalPropertyException extends RuntimeException {

    NotOptionalPropertyException(String errorMessage) {
        super(errorMessage);
    }

    NotOptionalPropertyException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
