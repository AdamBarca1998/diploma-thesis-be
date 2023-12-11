package sk.adambarca.managementframework.impl.typeconverter;

class TypeCantBeExtractedException extends RuntimeException {

    TypeCantBeExtractedException(String errorMessage) {
        super(errorMessage);
    }

    TypeCantBeExtractedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
