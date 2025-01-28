package br.com.compass.repository.dao;

import br.com.compass.model.Client;
import jakarta.persistence.EntityManager;

public class ClientDAO extends BaseDAO<Client> {

    EntityManager entityManager;
    
    public ClientDAO(EntityManager entityManager) {
        super(entityManager, Client.class);
        this.entityManager = entityManager;
    }

    public Client findByCpf(String cpf) {
        String rawQuery = "SELECT c FROM Client c WHERE c.cpf = :cpf";

        try {
            return entityManager.createQuery(rawQuery, Client.class)
                .setParameter("cpf", cpf)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
