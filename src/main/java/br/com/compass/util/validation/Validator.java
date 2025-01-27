package br.com.compass.util.validation;

@FunctionalInterface
public interface Validator {

    boolean isValid(String input);

}
