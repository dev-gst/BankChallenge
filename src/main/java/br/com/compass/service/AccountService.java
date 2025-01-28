package br.com.compass.service;

import br.com.compass.model.Account;
import br.com.compass.model.Client;
import br.com.compass.model.enumeration.AccountType;
import br.com.compass.repository.dao.AccountDAO;
import br.com.compass.util.validation.AccountInputCollector;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

public class AccountService {

    private final AccountInputCollector collector;
    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO, AccountInputCollector collector) {
        this.accountDAO = accountDAO;
        this.collector = collector;
    }

    public Optional<Account> createAccount(Client client) {
        AccountType accountType = collector.collectAccountType(
                """
                        Choose the account type:
                        1. Checking
                        2. Savings
                        3. Salary
                        """
        );

        Account account = new Account.Builder()
                .withClient(client)
                .withBalance(BigDecimal.ZERO)
                .withType(accountType)
                .withAccountNumber(generateAccountNumber(accountType))
                .build();

        try {
            accountDAO.startTransaction();
            accountDAO.save(account);
            accountDAO.commitTransaction();

            return Optional.of(account);
        } catch (Exception e) {
            accountDAO.rollbackTransaction();
            System.out.println("An error occurred while creating the account. Please try again.");

            return Optional.empty();
        }
    }

    public Account login(String cpf, String password) {
        return accountDAO.findByClientCpfAndPassword(cpf, password);
    }

    private String generateAccountNumber(AccountType accountType) {
        int digit1 = new Random().nextInt(9);
        int digit2 = new Random().nextInt(9);
        int digit3 = new Random().nextInt(9);
        int digit4 = new Random().nextInt(9);
        int digit5 = new Random().nextInt(9);

        String randomNumber = String.format("%d%d%d%d%d", digit1, digit2, digit3, digit4, digit5);

        String  accountNumber = switch (accountType) {
            case CHECKING -> String.format("001-%s", randomNumber);
            case SAVINGS -> String.format("002-%s", randomNumber);
            case SALARY -> String.format("003-%s", randomNumber);
        };

        Account account = accountDAO.findByAccountNumber(accountNumber);
        if (account != null) {
            return generateAccountNumber(accountType);
        }

        return accountNumber;
    }

}
