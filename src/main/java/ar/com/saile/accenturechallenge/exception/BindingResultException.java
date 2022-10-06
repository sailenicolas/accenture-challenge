package ar.com.saile.accenturechallenge.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class BindingResultException extends RuntimeException {

    List<FieldError> fieldErrors;

    public BindingResultException(BindingResult bindingResult) {

        this.fieldErrors = bindingResult.getFieldErrors().stream().toList();
    }
}
