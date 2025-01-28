package br.com.compass.util.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClientInputCollector {

    private static final Validator NAME_VALIDATOR =
            input -> !input.isBlank() && input.length() <= 100 && input.length() >= 2;

    private static final Validator CPF_VALIDATOR =
            input -> input.matches("\\d{11}");

    private static final Validator EMAIL_VALIDATOR =
            input -> input.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private static final Validator PASSWORD_VALIDATOR =
            input -> input.length() >= 8 && input.length() <= 20;

    private static final Validator PHONE_VALIDATOR =
            input -> input.matches("\\d{9,20}");

    private static final Validator BIRTH_DATE_VALIDATOR =
            input -> input.matches("^(0[1-9]|[12]\\d|3[01])-(0[1-9]|1[0-2])-\\d{4}$") &&
                    input.compareTo("01-01-1900") >= 0;

    private final UserInputCollector collector;

    public ClientInputCollector(UserInputCollector collector) {
        this.collector = collector;
    }

    public String collectName(String message) {
        return collector.collectInput(message, NAME_VALIDATOR);
    }

    public String collectCPF(String message) {
        return  collector.collectInput(message, CPF_VALIDATOR);
    }

    public String collectEmail(String message) {
        return  collector.collectInput(message, EMAIL_VALIDATOR);
    }

    public String collectPassword(String message) {
        return  collector.collectInput(message, PASSWORD_VALIDATOR);
    }

    public String collectPhone(String message) {
        return  collector.collectInput(message, PHONE_VALIDATOR);
    }

    public LocalDate collectBirthDate(String message) {
        String rawDate =  collector.collectInput(message, BIRTH_DATE_VALIDATOR);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return LocalDate.parse(rawDate, formatter);
    }

}
