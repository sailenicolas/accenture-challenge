package ar.com.saile.accenturechallenge.exception;

public class RecordNotFound extends RuntimeException{

    public RecordNotFound(String found) {
        super(found);
    }
}
