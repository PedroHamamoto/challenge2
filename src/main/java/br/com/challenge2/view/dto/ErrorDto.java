package br.com.challenge2.view.dto;

/**
 * Represents a error from a rest call
 */
public class ErrorDto {

    private String code;
    private String message;

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
