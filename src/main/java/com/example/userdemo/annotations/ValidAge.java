package com.example.userdemo.annotations;

import com.example.userdemo.validators.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation for ensuring that a user's age is valid.
 *
 * Usage:
 * Add this annotation to a field of type LocalDate representing the user's date of birth.
 * You can specify the minimum age required for the user in the `minAge` attribute or specify
 * the userdemo.validation.userMinAcceptableAge property in the application.properties file.
 *
 * Example:
 * ```
 * @ValidAge(minAge = 18)
 * private LocalDate dateOfBirth;
 * ```
 *
 * Validation:
 * The annotated field will be validated to ensure that the user's age is greater than or equal to the specified minimum age.
 * If the validation fails, an error message will be returned as specified in the annotation's message attribute.
 *
 * @see AgeValidator
 * @see java.time.LocalDate
 */
@Constraint(validatedBy = AgeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidAge {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "The User must be over: %d years old";

    int minAge() default 0;
}
