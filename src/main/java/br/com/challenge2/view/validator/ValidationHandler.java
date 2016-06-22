package br.com.challenge2.view.validator;

import br.com.challenge2.view.dto.Resource;
import br.com.challenge2.view.dto.ValidationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Handles the exceptions related to the models objects and returns the proper message(s)
 */
@ControllerAdvice
public class ValidationHandler {
    @Autowired
    private MessageSource msgSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity processValidationError(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BindingResult result = ex.getBindingResult();

        ArrayList<String> errors = new ArrayList<>();

        for (FieldError error : result.getFieldErrors()) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
            errors.add(msg);
        }

        ValidationDto validationDto = new ValidationDto(errors);
        Resource<ValidationDto> resource = new Resource<>(request.getRequestURI(), validationDto);

        return new ResponseEntity(resource, HttpStatus.BAD_REQUEST);
    }

}