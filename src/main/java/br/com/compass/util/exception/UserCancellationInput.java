package br.com.compass.util.exception;

public class UserCancellationInput extends  RuntimeException {

    public UserCancellationInput(String message) {
        super(message);
    }

}
