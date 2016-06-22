package br.com.challenge2.infrastructure.exception;

/**
 * This exception is thrown when something goes wrong in the server side
 */
public class InternalServerErrorException extends ApplicationException {

    public InternalServerErrorException() {
        super(ApplicationExceptionEnum.INTERNAL_SERVER_ERROR_EXCEPTION);
    }
}
