package sk.adambarca.managementframework.impl.typeconverter;

final class ConversionStrategyNotFoundException extends RuntimeException {

    ConversionStrategyNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    ConversionStrategyNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
