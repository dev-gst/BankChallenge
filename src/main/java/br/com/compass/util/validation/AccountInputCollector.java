package br.com.compass.util.validation;

import br.com.compass.model.Account;

public class AccountInputCollector {

    private static final Validator accountTypeValidator = input -> {
        try {
            Account.AccountType.valueOf(input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    };

    private final UserInputCollector collector;

    public AccountInputCollector(UserInputCollector collector) {
        this.collector = collector;
    }

    public Account.AccountType collectAccountType(String message) {
        String rawAccountType = collector.collectInput(message, accountTypeValidator);
        return Account.AccountType.valueOf(rawAccountType.toUpperCase());
    }

}
