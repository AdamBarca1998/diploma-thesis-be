package sk.adambarca.managementframework.impl.typeconverter;

final class NotValidTypeException extends RuntimeException {

    NotValidTypeException(String errorMessage) {
        super(errorMessage);
    }

    NotValidTypeException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
