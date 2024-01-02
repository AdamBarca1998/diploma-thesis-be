package sk.adambarca.managementframework.impl.typeconverter;

final class ConversionException extends RuntimeException {

    ConversionException(String errorMessage) {
        super(errorMessage);
    }

    ConversionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
