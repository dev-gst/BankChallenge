package br.com.compass.util.validation;

import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class UserInputCollectorTest {


    @Test
    void collectInput_validInput() {
        Scanner scanner = new Scanner("validInput");
        UserInputCollector collector = new UserInputCollector(scanner);
        Validator validator = input -> input.equals("validInput");
        String result = collector.collectInput("Enter input:", validator);
        assertEquals("validInput", result);
    }

    @Test
    void collectInput_invalidInputThenValidInput() {
        Scanner scanner = new Scanner("invalidInput\nvalidInput");
        UserInputCollector collector = new UserInputCollector(scanner);
        Validator validator = input -> input.equals("validInput");
        String result = collector.collectInput("Enter input:", validator);
        assertEquals("validInput", result);
    }

    @Test
    void collectInput_emptyInputThenValidInput() {
        Scanner scanner = new Scanner("\nvalidInput");
        UserInputCollector collector = new UserInputCollector(scanner);
        Validator validator = input -> input.equals("validInput");
        String result = collector.collectInput("Enter input:", validator);
        assertEquals("validInput", result);
    }

    @Test
    void collectInput_nullInputThenValidInput() {
        Scanner scanner = new Scanner("\nvalidInput");
        UserInputCollector collector = new UserInputCollector(scanner);
        Validator validator = input -> input.equals("validInput");
        String result = collector.collectInput("Enter input:", validator);
        assertEquals("validInput", result);
    }

}