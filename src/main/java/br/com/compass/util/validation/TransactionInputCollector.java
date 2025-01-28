package br.com.compass.util.validation;

import br.com.compass.util.validation.parser.BigDecimalParser;

import java.math.BigDecimal;

public class TransactionInputCollector {

    private static final Validator AMOUNT_VALIDATOR = input ->
            BigDecimalParser.of(input) != null;

    private final UserInputCollector collector;

    public TransactionInputCollector(UserInputCollector collector) {
        this.collector = collector;
    }

    public BigDecimal collectAmount(String message) {
        String input = collector.collectInput(message, AMOUNT_VALIDATOR);
        return BigDecimalParser.of(input);
    }
}
