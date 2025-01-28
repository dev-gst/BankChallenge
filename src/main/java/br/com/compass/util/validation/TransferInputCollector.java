package br.com.compass.util.validation;

import java.math.BigDecimal;

public class TransferInputCollector {

    private static final Validator AMOUNT_VALIDATOR = input -> {
        try {
            new BigDecimal(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    private final UserInputCollector userInputCollector;

    public TransferInputCollector(UserInputCollector inputCollector) {
        this.userInputCollector = inputCollector;
    }

    public BigDecimal collectAmount(String message) {
        String input = userInputCollector.collectInput(message, AMOUNT_VALIDATOR);
        return new BigDecimal(input);
    }

}
