package br.com.compass.repository.dao;

import br.com.compass.model.Transfer;
import jakarta.persistence.EntityManager;

public class TransferDAO extends BaseDAO<Transfer> {

    EntityManager entityManager;

    public TransferDAO(EntityManager entityManager) {
        super(entityManager, Transfer.class);
        this.entityManager = entityManager;
    }

}
