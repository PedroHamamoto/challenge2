package br.com.challenge2.view.dto;

import java.util.List;

/**
 * Represents the validations error messages from the request body
 */
public class ValidationDto {

    private String code = "400";
    private List<String> errors;

    public ValidationDto(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
