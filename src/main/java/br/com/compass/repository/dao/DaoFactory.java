package br.com.compass.repository.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DaoFactory {

    private static final String PERSISTENCE_UNIT_NAME = "bank-persistence-unit";

    private static final EntityManagerFactory emf = Persistence
            .createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    private static final EntityManager em = emf.createEntityManager();

    public <T> BaseDAO<T> createBaseDAO(Class<T> entityClass) {
        return new BaseDAO<>(em, entityClass);
    }

}
