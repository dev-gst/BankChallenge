package br.com.compass.util.validation;

import java.util.Scanner;

public class UserInputCollector {

    private final Scanner scanner;

    public UserInputCollector(Scanner scanner) {
        this.scanner = scanner;
    }

    public String collectInput(String message, Validator validator) {
        System.out.print(message);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input != null && validator.isValid(input)) {
                break;
            }
            System.out.println("Invalid input. Please, try again.");
            System.out.print(message);
        }

        return input;
    }

}
