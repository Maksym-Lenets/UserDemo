package com.example.userdemo.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PreviousDateValidatorTest {
    @InjectMocks
    PreviousDateValidator previousDateValidator;

    @Test
    void isValidTest() {
        assertTrue(previousDateValidator.isValid(LocalDate.now().minusDays(1L), null));
        assertFalse(previousDateValidator.isValid(LocalDate.now(), null));
        assertFalse(previousDateValidator.isValid(LocalDate.now().plusDays(1L), null));
    }
}