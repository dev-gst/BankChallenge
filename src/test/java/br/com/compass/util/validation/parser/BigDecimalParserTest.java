package br.com.compass.util.validation.parser;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BigDecimalParserTest {

    @Test
    void of_validBrazilianNumber() {
        assertEquals(new BigDecimal("1234.56"), BigDecimalParser.of("1.234,56"));
    }

    @Test
    void of_validUSNumber() {
        assertEquals(new BigDecimal("1234.56"), BigDecimalParser.of("1,234.56"));
    }

    @Test
    void of_invalidNumber() {
        assertNull(BigDecimalParser.of("invalid"));
    }

    @Test
    void of_nullNumber() {
        assertNull(BigDecimalParser.of(null));
    }

    @Test
    void of_blankNumber() {
        assertNull(BigDecimalParser.of(" "));
    }

    @Test
    void of_emptyNumber() {
        assertNull(BigDecimalParser.of(""));
    }

    @Test
    void of_numberWithInvalidCharacters() {
        assertNull(BigDecimalParser.of("1234.56abc"));
    }

    @Test
    void of_numberWithOnlyGroupSeparator() {
        assertEquals(new BigDecimal("1234"), BigDecimalParser.of("1.234"));
    }

    @Test
    void of_numberWithOnlyDecimalSeparator() {
        assertEquals(new BigDecimal("1234.56"), BigDecimalParser.of("1234,56"));
    }


}