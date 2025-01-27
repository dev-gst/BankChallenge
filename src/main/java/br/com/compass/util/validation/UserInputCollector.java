package br.com.compass.util.validation;

import java.util.Scanner;

public class UserInputCollector {

    public static String collectInput(String message, Validator validator, Scanner scanner) {
        System.out.print(message);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input != null && validator.isValid(input)) {
                break;
            }
            System.out.println("Invalid input. Please, try again.");
        }
        return input;
    }

}
