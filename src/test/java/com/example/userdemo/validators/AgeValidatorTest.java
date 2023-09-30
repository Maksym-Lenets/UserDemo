package com.example.userdemo.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgeValidatorTest {

    @InjectMocks
    private AgeValidator ageValidator;
    @Mock
    ConstraintValidatorContext context;
    @Mock
    ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Test
    void isValidCorrectAgeTest() {
        int minUserAge = 18;
        ageValidator.setDefaultUserMinAge(minUserAge);

        assertTrue(ageValidator.isValid(LocalDate.now().minusYears(minUserAge), context));
    }

    @Test
    void isValidCorrectNullDateTest() {
        assertTrue(ageValidator.isValid(null, context));
    }

    @Test
    void isValidInCorrectAgeTest() {
        int minUserAge = 18;
        ageValidator.setDefaultUserMinAge(minUserAge);
        when(context.getDefaultConstraintMessageTemplate()).thenReturn("message %d");
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        assertFalse(ageValidator.isValid(LocalDate.now().minusYears(minUserAge - 1), context));
    }

}