package br.com.compass.util.validation;

import br.com.compass.util.exception.UserCancellationInput;

import java.util.Scanner;

public class UserInputCollector {

    private static final String CANCEL_COMMAND = "cancel";

    private final Scanner scanner;

    public UserInputCollector(Scanner scanner) {
        this.scanner = scanner;
    }

    public String collectInput(String message, Validator validator) {
        System.out.print(message);

        String input;
        while (true) {
            input = scanner.nextLine();
            if (inputIsValid(input, validator)) break;

            System.out.println("Invalid input. Please, try again.");
            System.out.print(message);
        }

        return input;
    }

    private static boolean inputIsValid(String input, Validator validator) {
        if (input != null) {
            if (input.equals(CANCEL_COMMAND)) {
                throw new UserCancellationInput("User canceled the operation.");
            }

            return validator.isValid(input);
        }

        return false;
    }

}
