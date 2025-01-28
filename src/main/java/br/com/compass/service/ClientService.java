package br.com.compass.service;

import br.com.compass.model.Client;
import br.com.compass.repository.dao.BaseDAO;
import br.com.compass.util.validation.ClientInputCollector;

import java.time.LocalDate;
import java.util.Optional;

public class ClientService {

    private final BaseDAO<Client> clientDAO;
    private final ClientInputCollector collector;

    public ClientService( ClientInputCollector collector, BaseDAO<Client> clientDAO) {
        this.clientDAO = clientDAO;
        this.collector = collector;
    }

    public Optional<Client> createClient() {
        Client client = getUserInfo();
        try {
            clientDAO.startTransaction();
            clientDAO.save(client);
            clientDAO.commitTransaction();

            return Optional.of(client);
        } catch (Exception e) {
            clientDAO.rollbackTransaction();
            System.out.println("An error occurred while creating the client.");

            return Optional.empty();
        }
    }

    private Client getUserInfo() {
        Client client;

        String firstName = collector.collectName("Enter your first name: ");
        String lastName = collector.collectName("Enter your last name: ");
        String phone = collector.collectPhone("Enter your phone: ");
        String email = collector.collectEmail("Enter your email: ");
        String password = collector.collectPassword("Enter your password: ");
        String cpf = collector.collectCPF("Enter your cpf: ");
        LocalDate birthDate = collector.collectBirthDate("Enter your birth date: ");

        client = new Client.Builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withPhone(phone)
                .withEmail(email)
                .withPassword(password)
                .withBirthDate(birthDate)
                .withCpf(cpf)
                .build();

        return client;
    }

}
