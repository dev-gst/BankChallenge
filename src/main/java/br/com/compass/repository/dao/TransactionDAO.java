package br.com.compass.repository.dao;

import br.com.compass.model.Account;
import br.com.compass.model.Transaction;
import jakarta.persistence.EntityManager;

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

}
