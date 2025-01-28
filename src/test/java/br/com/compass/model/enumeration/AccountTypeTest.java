package br.com.compass.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTypeTest {

    @Test
    void fromCode_validCode() {
        assertEquals(AccountType.CHECKING, AccountType.fromCode(1));
        assertEquals(AccountType.SAVINGS, AccountType.fromCode(2));
        assertEquals(AccountType.SALARY, AccountType.fromCode(3));
    }

    @Test
    void fromCode_invalidCode() {
        assertThrows(IllegalArgumentException.class, () -> AccountType.fromCode(0));
        assertThrows(IllegalArgumentException.class, () -> AccountType.fromCode(4));
    }

    @Test
    void fromDescription_validDescription() {
        assertEquals(AccountType.CHECKING, AccountType.fromDescription("Checking"));
        assertEquals(AccountType.SAVINGS, AccountType.fromDescription("Savings"));
        assertEquals(AccountType.SALARY, AccountType.fromDescription("Salary"));
    }

    @Test
    void fromDescription_invalidDescription() {
        assertThrows(IllegalArgumentException.class, () -> AccountType.fromDescription("Invalid"));
        assertThrows(IllegalArgumentException.class, () -> AccountType.fromDescription(""));
    }

    @Test
    void fromDescription_caseInsensitive() {
        assertEquals(AccountType.CHECKING, AccountType.fromDescription("checking"));
        assertEquals(AccountType.SAVINGS, AccountType.fromDescription("savings"));
        assertEquals(AccountType.SALARY, AccountType.fromDescription("salary"));
    }

}