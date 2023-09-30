package com.example.userdemo.annotations;

import com.example.userdemo.validators.PreviousDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation for ensuring that a date value is before the
 * current date.
 *
 * Usage:
 * Add this annotation to a field of type LocalDate to enforce that the date value is in the past,
 * i.e., before the current date.
 *
 * Example:
 * ```
 * @BeforeCurrentDate
 * private LocalDate dateOfBirth;
 * ```
 *
 * Validation:
 * The annotated field will be validated to ensure that the date is before the current date.
 * If the validation fails, an error message will be returned as specified in the annotation's message attribute.
 *
 * @see PreviousDateValidator
 * @see java.time.LocalDate
 */

@Constraint(validatedBy = PreviousDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BeforeCurrentDate {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "The defined date must precede the current date";
}
