package br.com.fiap.baitersburger.adapters.in.controller.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPFValidatorTest {

    private final CPFValidator validator = new CPFValidator();


    @Test
    void returnsFalseWhenCpfIsNull() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void returnsFalseWhenCpfIsEmpty() {
        assertFalse(validator.isValid("", null));
    }

    @Test
    void returnsFalseWhenCpfHasInvalidLength() {
        assertFalse(validator.isValid("123456789", null));
    }

    @Test
    void returnsFalseWhenCpfHasInvalidCharacters() {
        assertFalse(validator.isValid("123.abc.789-00", null));
    }

    @Test
    void returnsFalseWhenCpfFailsFirstDigitValidation() {
        assertFalse(validator.isValid("12345678900", null));
    }

    @Test
    void returnsFalseWhenCpfFailsSecondDigitValidation() {
        assertFalse(validator.isValid("12345678901", null));
    }

    @Test
    void returnsTrueWhenCpfIsValid() {
        assertTrue(validator.isValid("12345678909", null));
    }
}