package br.com.compass;

import br.com.compass.model.Account;
import br.com.compass.repository.dao.AccountDAO;
import br.com.compass.repository.dao.ClientDAO;
import br.com.compass.repository.dao.DaoFactory;
import br.com.compass.repository.dao.TransactionDAO;
import br.com.compass.service.AccountService;
import br.com.compass.service.AuthenticationService;
import br.com.compass.service.ClientService;
import br.com.compass.service.TransactionService;
import br.com.compass.util.validation.AccountInputCollector;
import br.com.compass.util.validation.ClientInputCollector;
import br.com.compass.util.validation.TransactionInputCollector;
import br.com.compass.util.validation.UserInputCollector;

import java.util.Scanner;

public class App {

    private static ClientDAO clientDAO;
    private static AccountDAO accountDAO;
    private static TransactionDAO transactionDAO;

    private static TransactionService transactionService;
    private static AuthenticationService authenticationService;

    private static Account accountLoggedIn;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        instantiateDAOs();
        instantiateServices(scanner);

        mainMenu(scanner);
        
        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner) {
        if (accountLoggedIn != null) {
            bankMenu(scanner);
            return;
        }

        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    accountLoggedIn = authenticationService.login();
                    bankMenu(scanner);
                    break;
                case 2:
                    System.out.println("Account Opening. Type cancel anytime to return to the main menu.");
                    authenticationService.createAccount();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void bankMenu(Scanner scanner) {
        if (accountLoggedIn == null) {
            return;
        }

        while (true) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Deposit.");
                    transactionService.deposit(accountLoggedIn);
                    break;
                case 2:
                    System.out.println("Withdraw.");
                    transactionService.withdraw(accountLoggedIn);
                    break;
                case 3:
                    System.out.println("Check Balance.");
                    System.out.println("Balance: " + accountLoggedIn.getBalance());
                    break;
                case 4:
                    System.out.println("Transfer.");
                    transactionService.transfer(accountLoggedIn);
                    break;
                case 5:
                    System.out.println("Bank Statement.");
                    transactionService.showTransactions(accountLoggedIn);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void instantiateDAOs() {
        clientDAO = DaoFactory.createClientDAO();
        accountDAO = DaoFactory.createAccountDAO();
        transactionDAO = DaoFactory.createTransactionDAO();
    }

    private static void instantiateServices(Scanner scanner) {
        UserInputCollector userInputCollector = new UserInputCollector(scanner);

        ClientInputCollector clientInputCollector = new ClientInputCollector(userInputCollector);
        AccountInputCollector accountInputCollector = new AccountInputCollector(userInputCollector);
        TransactionInputCollector transactionInputCollector = new TransactionInputCollector(userInputCollector);

        ClientService clientService = new ClientService(clientDAO, clientInputCollector);
        AccountService accountService = new AccountService(accountDAO, accountInputCollector);

        transactionService = new TransactionService(transactionDAO, accountService, transactionInputCollector);
        authenticationService = new AuthenticationService(accountService, clientService, clientInputCollector);
    }

}
