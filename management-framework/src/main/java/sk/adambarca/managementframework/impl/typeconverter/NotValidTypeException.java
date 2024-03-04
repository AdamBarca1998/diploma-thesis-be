package sk.adambarca.managementframework.impl.typeconverter;

public final class NotValidTypeException extends RuntimeException {

    NotValidTypeException(String errorMessage) {
        super(errorMessage);
    }
}
