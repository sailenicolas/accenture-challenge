package ar.com.saile.accenturechallenge.controller;

import ar.com.saile.accenturechallenge.exception.BindingResultException;
import ar.com.saile.accenturechallenge.exception.LoginFailedException;
import ar.com.saile.accenturechallenge.exception.RecordNotFound;
import ar.com.saile.accenturechallenge.exception.UserNotAuthorized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(LoginFailedException.class)
    protected ResponseEntity<?> handleLoginFailed(LoginFailedException ex) {

        return new ResponseEntity<>( List.of(ex.getMessage()), HttpStatus.UNAUTHORIZED );
    }

    @ExceptionHandler(UserNotAuthorized.class)
    protected ResponseEntity<?> handleAuthFail(UserNotAuthorized ex) {

        return new ResponseEntity<>( List.of(ex.getMessage()), HttpStatus.UNAUTHORIZED );
    }
    @ExceptionHandler(RecordNotFound.class)
    protected ResponseEntity<?> handleNotFound(RecordNotFound ex) {

        return new ResponseEntity<>( List.of(ex.getMessage()), BAD_REQUEST );
    }


    @ExceptionHandler(BindingResultException.class)
    protected ResponseEntity<?> handleBindingResultException(BindingResultException ex) {

        Map<String, Object> tokens = new HashMap<>();
        List<Map<String, Object>> token2 = ex.getFieldErrors().stream().map( vale -> {
            String defaultMessage = "FIELD ERROR";
            String code = "CODE ERROR";
            if (vale.getDefaultMessage() != null)
                defaultMessage = vale.getDefaultMessage();
            if (vale.getCode() != null)
                code = vale.getCode();
            return Map.<String, Object>ofEntries(
                    Map.entry( "message", defaultMessage ),
                    Map.entry( "code", code ),
                    Map.entry( "field", vale.getField() )
            );
        } ).collect( Collectors.toList() );

        tokens.put( "errorMessage", "Hay errores en lo enviado" );
        tokens.put( "errorFields", token2 );
        tokens.put( "errorCode", BAD_REQUEST.series() );
        return new ResponseEntity<>( tokens, BAD_REQUEST );
    }

}
