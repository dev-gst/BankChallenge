package br.com.compass.util.validation;

import br.com.compass.model.enumeration.AccountType;

public class AccountInputCollector {

    private static final Validator ACCOUNT_TYPE_VALIDATOR = input -> {
        try {
            AccountType.fromCode(Integer.parseInt(input));
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    };

    private static final Validator ACCOUNT_NUMBER_VALIDATOR = input -> input.matches("[\\d-]{10}");

    private final UserInputCollector collector;

    public AccountInputCollector(UserInputCollector collector) {
        this.collector = collector;
    }

    public AccountType collectAccountType(String message) {
        String rawAccountType = collector.collectInput(message, ACCOUNT_TYPE_VALIDATOR);
        return AccountType.fromCode(Integer.parseInt(rawAccountType));
    }

    public String collectAccountNumber(String message) {
        return collector.collectInput(message, ACCOUNT_NUMBER_VALIDATOR);
    }

}
