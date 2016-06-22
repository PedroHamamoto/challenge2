package br.com.challenge2.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Enum representing all {@link ApplicationException} attributes
 */
public enum ApplicationExceptionEnum {
    ADDRESS_NOT_FOUND_EXCEPTION("404", "Endereço não encontrado", HttpStatus.NOT_FOUND),
    INVALID_CEP_EXCEPTION("400", "Cep inválido", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR_EXCEPTION("500", "Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);

    private String code;
    private String message;
    private HttpStatus httpStatus;

    ApplicationExceptionEnum(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
