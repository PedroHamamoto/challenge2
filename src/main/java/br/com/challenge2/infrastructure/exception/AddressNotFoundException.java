package br.com.challenge2.infrastructure.exception;

/**
 * This exception is thrown when the Address isn't found
 */
public class AddressNotFoundException extends ApplicationException {

    public AddressNotFoundException() {
        super(ApplicationExceptionEnum.ADDRESS_NOT_FOUND_EXCEPTION);
    }
}
