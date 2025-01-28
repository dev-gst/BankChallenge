package br.com.compass.repository.dao;

import br.com.compass.model.Transaction;
import jakarta.persistence.EntityManager;

public class TransactionDAO extends BaseDAO<Transaction> {

    EntityManager entityManager;

    public TransactionDAO(EntityManager entityManager) {
        super(entityManager, Transaction.class);
        this.entityManager = entityManager;
    }

}
