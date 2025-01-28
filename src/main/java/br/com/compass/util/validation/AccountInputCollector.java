package br.com.compass.util.validation;

import br.com.compass.model.enumeration.AccountType;
import br.com.compass.util.validation.parser.BigDecimalParser;

import java.math.BigDecimal;

public class AccountInputCollector {

    private static final Validator accountTypeValidator = input -> {
        try {
            AccountType.fromCode(Integer.parseInt(input));
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    };

    private static final Validator AMOUNT_VALIDATOR = input ->
            BigDecimalParser.of(input) != null;

    private final UserInputCollector collector;

    public AccountInputCollector(UserInputCollector collector) {
        this.collector = collector;
    }

    public AccountType collectAccountType(String message) {
        String rawAccountType = collector.collectInput(message, accountTypeValidator);
        return AccountType.fromCode(Integer.parseInt(rawAccountType));
    }

    public BigDecimal collectAmount(String message) {
        String input = collector.collectInput(message, AMOUNT_VALIDATOR);
        return BigDecimalParser.of(input);
    }

}
