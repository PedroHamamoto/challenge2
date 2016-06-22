package br.com.challenge2.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * This exception is thrown when the {@link br.com.challenge2.infrastructure.gateway.ChallengeGateway}
 * verifies that the CEP isi invalid
 */
public class InvalidCepChallengeGatewayException extends ApplicationException {
    public InvalidCepChallengeGatewayException(String code, String message) {
        super(code, message, HttpStatus.BAD_REQUEST);

    }
}
