package br.com.compass.service;

import br.com.compass.model.Account;
import br.com.compass.model.Transaction;
import br.com.compass.model.enumeration.TransactionType;
import br.com.compass.repository.dao.AccountDAO;
import br.com.compass.repository.dao.TransactionDAO;

import java.math.BigDecimal;

public class TransactionService {

    private final TransactionDAO transferDAO;
    private final AccountDAO accountDAO;

    public TransactionService(
            TransactionDAO transferDAO,
            AccountDAO accountDAO
    ) {
        this.transferDAO = transferDAO;
        this.accountDAO = accountDAO;
    }

    public void transfer(
            Account sourceAccount,
            Account destinationAccount,
            BigDecimal value
    ) {
        if (!checkTransferValues(sourceAccount, destinationAccount, value)) {
            return;
        }

        accountDAO.startTransaction();
        transferDAO.startTransaction();

        Transaction transaction = new Transaction.Builder()
                .withSourceAccount(sourceAccount)
                .withDestinationAccount(destinationAccount)
                .withAmount(value)
                .withType(TransactionType.TRANSFER)
                .build();

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(value));
        destinationAccount.setBalance(destinationAccount.getBalance().add(value));

        accountDAO.updateBalance(sourceAccount, sourceAccount.getBalance());
        accountDAO.updateBalance(destinationAccount, destinationAccount.getBalance());
        transferDAO.save(transaction);

        accountDAO.commitTransaction();
        transferDAO.commitTransaction();
    }

    public void deposit(Account destinationAccount, BigDecimal value) {
        if (!valueGreaterThenZero(value)) {
            return;
        }

        accountDAO.startTransaction();

        Transaction transaction = new Transaction.Builder()
                .withDestinationAccount(destinationAccount)
                .withAmount(value)
                .withType(TransactionType.DEPOSIT)
                .build();

        destinationAccount.setBalance(destinationAccount.getBalance().add(value));

        accountDAO.updateBalance(destinationAccount, destinationAccount.getBalance());
        transferDAO.save(transaction);

        accountDAO.commitTransaction();
    }

    public void withdraw(Account sourceAccount, BigDecimal value) {
        if (hasBalance(sourceAccount, value)) {
            return;
        }

        if (!valueGreaterThenZero(value)) {
            return;
        }

        accountDAO.startTransaction();

        Transaction transaction = new Transaction.Builder()
                .withSourceAccount(sourceAccount)
                .withAmount(value)
                .withType(TransactionType.WITHDRAW)
                .build();

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(value));

        accountDAO.updateBalance(sourceAccount, sourceAccount.getBalance());
        transferDAO.save(transaction);

        accountDAO.commitTransaction();
    }

    private static boolean checkTransferValues(Account sourceAccount, Account destinationAccount, BigDecimal value) {
        if (!hasBalance(sourceAccount, value)) {
            System.out.println("Insufficient funds.");
            return false;
        }

        if (isSameAccount(sourceAccount, destinationAccount)) {
            System.out.println("Source and destination accounts must be different.");
            return false;
        }

        if (!valueGreaterThenZero(value)) {
            System.out.println("The value must be greater than zero.");
            return false;
        }

        return true;
    }

    private static boolean hasBalance(Account account, BigDecimal value) {
        return account.getBalance().compareTo(value) >= 0;
    }

    private static boolean isSameAccount(Account sourceAccount, Account destinationAccount) {
        return sourceAccount.equals(destinationAccount);
    }

    private static boolean valueGreaterThenZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

}
