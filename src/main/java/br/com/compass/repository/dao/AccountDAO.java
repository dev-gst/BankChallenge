package br.com.compass.repository.dao;

import br.com.compass.model.Account;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;

public class AccountDAO extends BaseDAO<Account> {

    EntityManager entityManager;

    public AccountDAO(EntityManager entityManager) {
        super(entityManager, Account.class);
        this.entityManager = entityManager;
    }

    public Account findByAccountNumber(String accountNumber) {
        String rawQuery = """
                SELECT a
                    FROM Account a
                    WHERE a.accountNumber = :accountNumber
                """;
        try {
            return entityManager.createQuery(rawQuery, Account.class)
                    .setParameter("accountNumber", accountNumber)
                    .getSingleResult();
        } catch (Exception ignored) {
            return null;
        }
    }

    public Account findByClientCpfAndPassword(String cpf, String password) {
        String rawQuery = """
                SELECT a
                    FROM Account a
                    WHERE a.client.cpf = :cpf
                    AND a.client.password = :password
                """;
        try {
            return entityManager.createQuery(rawQuery, Account.class)
                    .setParameter("cpf", cpf)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void updateBalance(Account account, BigDecimal newBalance) {
        entityManager.createQuery("UPDATE Account a SET a.balance = :newBalance WHERE a.id = :accountId")
                .setParameter("newBalance", newBalance)
                .setParameter("accountId", account.getId())
                .executeUpdate();
    }

    public void updateBalance(String accountNumber, BigDecimal newBalance) {
        entityManager.createQuery("UPDATE Account a SET a.balance = :newBalance WHERE a.accountNumber = :accountNumber")
                .setParameter("newBalance", newBalance)
                .setParameter("accountNumber", accountNumber)
                .executeUpdate();
    }

}
