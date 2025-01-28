package br.com.compass.util.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientInputCollectorTest {

    private UserInputCollector userInputCollector;
    private ClientInputCollector clientInputCollector;

    @BeforeEach
    void setUp() {
        userInputCollector = mock(UserInputCollector.class);
        clientInputCollector = new ClientInputCollector(userInputCollector);
    }

    @Test
    void collectName_validName() {
        when(userInputCollector.collectInput(anyString(), any())).thenReturn("John Doe");
        String result = clientInputCollector.collectName("Enter name:");
        assertEquals("John Doe", result);
    }

    @Test
    void collectCPF_validCPF() {
        when(userInputCollector.collectInput(anyString(), any())).thenReturn("123.456.789-00");
        String result = clientInputCollector.collectCPF("Enter CPF:");
        assertEquals("123.456.789-00", result);
    }

    @Test
    void collectEmail_validEmail() {
        when(userInputCollector.collectInput(anyString(), any())).thenReturn("test@example.com");
        String result = clientInputCollector.collectEmail("Enter email:");
        assertEquals("test@example.com", result);
    }

    @Test
    void collectPassword_validPassword() {
        when(userInputCollector.collectInput(anyString(), any())).thenReturn("password123");
        String result = clientInputCollector.collectPassword("Enter password:");
        assertEquals("password123", result);
    }

    @Test
    void collectPhone_validPhone() {
        when(userInputCollector.collectInput(anyString(), any())).thenReturn("123456789");
        String result = clientInputCollector.collectPhone("Enter phone:");
        assertEquals("123456789", result);
    }

    @Test
    void collectBirthDate_validBirthDate() {
        when(userInputCollector.collectInput(anyString(), any())).thenReturn("25-12-1990");
        LocalDate result = clientInputCollector.collectBirthDate("Enter birth date:");
        assertEquals(LocalDate.of(1990, 12, 25), result);

    }

}