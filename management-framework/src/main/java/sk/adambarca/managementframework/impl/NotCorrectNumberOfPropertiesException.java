package sk.adambarca.managementframework.impl;

final class NotCorrectNumberOfPropertiesException extends RuntimeException {

    NotCorrectNumberOfPropertiesException(String errorMessage) {
        super(errorMessage);
    }

    NotCorrectNumberOfPropertiesException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
