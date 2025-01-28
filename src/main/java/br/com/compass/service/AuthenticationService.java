package br.com.compass.service;

import br.com.compass.model.Account;
import br.com.compass.model.Client;
import br.com.compass.util.exception.UserCancellationInput;
import br.com.compass.util.validation.ClientInputCollector;

import java.util.Optional;

public class AuthenticationService {

    private final AccountService accountService;
    private final ClientService clientService;
    private final ClientInputCollector clientInputCollector;

    public AuthenticationService(
            AccountService accountService,
            ClientService clientService,
            ClientInputCollector clientInputCollector
    ) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.clientInputCollector = clientInputCollector;
    }

    public void createAccount() {
        try {
            startAccountCreation();
        } catch (UserCancellationInput ignored) {
            System.out.println("You canceled the account creation.");
        }
    }

    private void startAccountCreation() {
        Optional<Client> client = clientService.createClient();
        client.ifPresent(accountService::createAccount);
    }

    public Account login() {
        System.out.println("Login");

        Account account;
        try {
            String cpf = clientInputCollector.collectCPF("Enter your CPF: ");
            String password = clientInputCollector.collectPassword("Enter your password: ");
            account = accountService.login(cpf, password);
        } catch (UserCancellationInput ignored) {
            System.out.println("You canceled the login.");
            return null;
        }


        if (account == null) {
            System.out.println("Invalid credentials.");
            return null;
        }

        return account;
    }

}
