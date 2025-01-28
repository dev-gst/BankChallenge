package br.com.compass.service;

import br.com.compass.model.Client;
import br.com.compass.repository.dao.ClientDAO;
import br.com.compass.util.validation.ClientInputCollector;

import java.time.LocalDate;
import java.util.Optional;

public class ClientService {

    private final ClientDAO clientDAO;
    private final ClientInputCollector collector;

    public ClientService(ClientDAO clientDAO, ClientInputCollector collector) {
        this.clientDAO = clientDAO;
        this.collector = collector;
    }

    public Optional<Client> findClientByCpf(String email) {
        return Optional.of(clientDAO.findByCpf(email));
    }

    public Optional<Client> createClient() {
        Client client = getUserInfo();

        Optional<Client> clientFound = findClientByCpf(client.getCpf());
        if (clientFound.isPresent()) {
            System.out.println("This cpf is already registered. Please try again.");
            return Optional.empty();
        }

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
        String password = collector.collectPassword("Enter your password (8-20 characters): ");
        String cpf = collector.collectCPF("Enter your cpf (only numbers): ");
        LocalDate birthDate = collector.collectBirthDate("Enter your birth date (dd-MM-yyyy): ");

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
