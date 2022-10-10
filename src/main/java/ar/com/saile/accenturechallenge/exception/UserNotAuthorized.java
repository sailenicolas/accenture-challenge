package ar.com.saile.accenturechallenge.exception;

import java.io.Serial;

public class UserNotAuthorized extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotAuthorized(String message) {

        super( message );
    }
}
