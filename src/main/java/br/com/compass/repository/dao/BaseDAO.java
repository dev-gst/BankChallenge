package br.com.compass.repository.dao;

import br.com.compass.repository.BasicCRUD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

public class BaseDAO<T> implements BasicCRUD<T> {

    private final Session session;
    private final EntityManager entityManager;
    private final Class<T> entityClass;

    public BaseDAO(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        session = entityManager.unwrap(Session.class);
    }

    public void startTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        if (!isTransactionActive()) {
            transaction.begin();
        }
    }

    public void commitTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        if (transaction != null && transaction.isActive()) {
            transaction.commit();
        }
    }

    public void rollbackTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }

    protected boolean isTransactionActive() {
        return session != null &&
                session.getTransaction() != null &&
                session.getTransaction().isActive();
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

}
