package br.com.challenge2.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * A generic application exception
 */
public class ApplicationException extends RuntimeException {

    private String code;
    private HttpStatus httpStatus;

    public ApplicationException(ApplicationExceptionEnum applicationExceptionEnum) {
        super(applicationExceptionEnum.getMessage());
        this.code = applicationExceptionEnum.getCode();
        this.httpStatus = applicationExceptionEnum.getHttpStatus();
    }

    public ApplicationException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }


    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
