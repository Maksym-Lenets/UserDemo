package com.example.userdemo.validators;

import com.example.userdemo.annotations.BeforeCurrentDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * Custom validator for validating whether a date is before the current date
 * based on the BeforeCurrentDate annotation.
 */
public class PreviousDateValidator implements ConstraintValidator<BeforeCurrentDate, LocalDate> {
    /**
     * Validates if a date is before the current date.
     *
     * @param date    The date to be validated.
     * @param context The constraint validator context.
     * @return True if the date is valid (before the current date or null);
     *         otherwise, false.
     */
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        return date == null || date.isBefore(LocalDate.now());
    }

    @Override
    public void initialize(BeforeCurrentDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
