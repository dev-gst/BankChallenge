package br.com.compass.service;

import br.com.compass.model.Account;
import br.com.compass.model.Transaction;
import br.com.compass.model.enumeration.TransactionType;
import br.com.compass.repository.dao.TransactionDAO;
import br.com.compass.util.exception.UserCancellationInput;
import br.com.compass.util.validation.TransactionInputCollector;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionService {

    private final TransactionDAO transactionDAO;
    private final AccountService accountService;
    private final TransactionInputCollector collector;

    public TransactionService(
            TransactionDAO transactionDAO,
            AccountService accountService,
            TransactionInputCollector collector
    ) {
        this.transactionDAO = transactionDAO;
        this.accountService = accountService;
        this.collector = collector;
    }

    public void transfer(Account sourceAccount) {
        Account destinationAccount;
        try {
            destinationAccount = accountService.findDestinationByAccountNumber().orElse(null);
        } catch (UserCancellationInput ignored) {
            System.out.println("Operation canceled.");
            return;
        }

        if (destinationAccount == null) {
            System.out.println("Destination account not found.");
            return;
        }

        BigDecimal value;
        try {
            value = getAmount();
        } catch (UserCancellationInput ignored) {
            System.out.println("Operation canceled.");
            return;
        }

        if (!checkTransferValues(sourceAccount, destinationAccount, value)) {
            return;
        }

        try {
            transactionDAO.startTransaction();

            Transaction transaction = new Transaction.Builder()
                    .withSourceAccount(sourceAccount)
                    .withDestinationAccount(destinationAccount)
                    .withAmount(value)
                    .withType(TransactionType.TRANSFER)
                    .build();

            transactionDAO.save(transaction);

            BigDecimal sourceNewBalance = sourceAccount.getBalance().subtract(value);
            BigDecimal destinationNewBalance = destinationAccount.getBalance().add(value);

            accountService.updateBalance(sourceAccount, sourceNewBalance);
            accountService.updateBalance(destinationAccount, destinationNewBalance);

            transactionDAO.commitTransaction();
        } catch (Exception ignored) {
            transactionDAO.rollbackTransaction();
            System.out.println("An error occurred while transferring the amount.");
        }
    }

    public void deposit(Account destinationAccount) {
        BigDecimal value;
        try {
            value = getAmount();
        } catch (UserCancellationInput ignored) {
            System.out.println("Operation canceled.");
            return;
        }

        if (valueIsNegativeOrZero(value)) {
            System.out.println("The value must be greater than zero.");
            return;
        }

        try {
            transactionDAO.startTransaction();

            Transaction transaction = new Transaction.Builder()
                    .withDestinationAccount(destinationAccount)
                    .withAmount(value)
                    .withType(TransactionType.DEPOSIT)
                    .build();

            transactionDAO.save(transaction);

            BigDecimal newBalance = destinationAccount.getBalance().add(value);

            accountService.updateBalance(destinationAccount, newBalance);

            transactionDAO.commitTransaction();
        } catch (Exception ignored) {
            transactionDAO.rollbackTransaction();
            System.out.println("An error occurred while depositing the amount.");
        }
    }

    public void withdraw(Account sourceAccount) {
        BigDecimal value;
        try {
            value = getAmount();
        } catch (UserCancellationInput ignored) {
            System.out.println("Operation canceled.");
            return;
        }

        if (missingFunds(sourceAccount, value)) {
            System.out.println("Insufficient funds.");
            return;
        }

        if (valueIsNegativeOrZero(value)) {
            System.out.println("The value must be greater than zero.");
            return;
        }

        try {
            transactionDAO.startTransaction();

            Transaction transaction = new Transaction.Builder()
                    .withSourceAccount(sourceAccount)
                    .withAmount(value)
                    .withType(TransactionType.WITHDRAW)
                    .build();

            transactionDAO.save(transaction);

            BigDecimal newBalance = sourceAccount.getBalance().subtract(value);

            accountService.updateBalance(sourceAccount, newBalance);

            transactionDAO.commitTransaction();
        } catch (Exception ignored) {
            transactionDAO.rollbackTransaction();
            System.out.println("An error occurred while withdrawing the amount.");
        }
    }

    public void showTransactions(Account account) {
        try {
            List<Transaction> transactions = transactionDAO.findByAccount(account);
            transactions.stream()
                    .map(this::formatTransactionLine)
                    .forEach(System.out::println);
        } catch (Exception ignored) {
            transactionDAO.rollbackTransaction();
            System.out.println("An error occurred while listing the transactions.");
        }
    }

    private BigDecimal getAmount() {
        return collector.collectAmount("Enter the amount: ");
    }

    private static boolean checkTransferValues(Account sourceAccount, Account destinationAccount, BigDecimal value) {
        if (missingFunds(sourceAccount, value)) {
            System.out.println("Insufficient funds.");
            return false;
        }

        if (isSameAccount(sourceAccount, destinationAccount)) {
            System.out.println("Source and destination accounts must be different.");
            return false;
        }

        if (valueIsNegativeOrZero(value)) {
            System.out.println("The value must be greater than zero.");
            return false;
        }

        return true;
    }

    private static boolean missingFunds(Account account, BigDecimal value) {
        return account.getBalance().compareTo(value) < 0;
    }

    private static boolean isSameAccount(Account sourceAccount, Account destinationAccount) {
        return sourceAccount.equals(destinationAccount);
    }

    private static boolean valueIsNegativeOrZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) <= 0;
    }

    private String formatTransactionLine(Transaction transaction) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        return switch (transaction.getType()) {
            case TRANSFER -> String.format(
                    "Transaction ID: %d | Type: %s | Amount: %s | Date: %s | Source Account: %s | Destination Account: %s",
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getAmount(),
                    formatter.format(transaction.createdAt()),
                    transaction.getSourceAccount().getAccountNumber(),
                    transaction.getDestinationAccount().getAccountNumber()
            );
            case DEPOSIT -> String.format(
                    "Transaction ID: %d | Type: %s | Amount: %s | Date: %s | Destination Account: %s",
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getAmount(),
                    formatter.format(transaction.createdAt()),
                    transaction.getDestinationAccount().getAccountNumber()
            );
            default -> String.format(
                    "Transaction ID: %d | Type: %s | Amount: %s | Date: %s | Source Account: %s",
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getAmount(),
                    formatter.format(transaction.createdAt()),
                    transaction.getSourceAccount().getAccountNumber()
            );
        };
    }

}
