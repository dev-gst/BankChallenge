package br.com.compass.util.validation;

import br.com.compass.model.enumeration.AccountType;

public class AccountInputCollector {

    private static final Validator accountTypeValidator = input -> {
        try {
            AccountType.fromCode(Integer.parseInt(input));
            return true;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    };

    private final UserInputCollector collector;

    public AccountInputCollector(UserInputCollector collector) {
        this.collector = collector;
    }

    public AccountType collectAccountType(String message) {
        String rawAccountType = collector.collectInput(message, accountTypeValidator);
        return AccountType.fromCode(Integer.parseInt(rawAccountType));
    }

}
