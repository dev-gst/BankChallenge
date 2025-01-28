package br.com.compass.repository.dao;

import br.com.compass.model.Account;
import br.com.compass.model.Transaction;
import br.com.compass.model.enumeration.AccountType;
import br.com.compass.model.enumeration.TransactionType;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends BaseDAO<Transaction> {

    EntityManager entityManager;

    public TransactionDAO(EntityManager entityManager) {
        super(entityManager, Transaction.class);
        this.entityManager = entityManager;
    }

    public List<Transaction> findByAccount(Account account) {
        String query = """
            SELECT t
            FROM Transaction t
            WHERE t.sourceAccount = :account
             OR t.destinationAccount = :account
        """;

        try {
            return entityManager.createQuery(query, Transaction.class)
                    .setParameter("account", account)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    public BigDecimal getTodayTotalByTypeAndAccount(
            Account account,
            TransactionType type,
            Instant min,
            Instant max
    ) {
        String query = """
            SELECT SUM(t.amount)
            FROM Transaction t
            WHERE (t.sourceAccount = :account OR t.destinationAccount = :account)
             AND t.type = :type
             AND t.createdAt >= :min
             AND t.createdAt <= :max
        """;

        try {
            return entityManager.createQuery(query, BigDecimal.class)
                    .setParameter("account", account)
                    .setParameter("type", type)
                    .setParameter("min", min)
                    .setParameter("max", max)
                    .getSingleResult();
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

}
