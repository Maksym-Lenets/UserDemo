package com.example.userdemo.validators;

import com.example.userdemo.annotations.ValidAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

/**
 * Custom validator for age validation based on the ValidAge annotation.
 */
@Getter
@Setter
public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {
    private int customUserMinAge;

    @Value("${userdemo.validation.userMinAcceptableAge}")
    private int defaultUserMinAge;

    /**
     * Validates if a user's age is greater than or equal to the specified minimum
     * age.
     *
     * @param dateOfBirth The date of birth of the user.
     * @param context     The constraint validator context.
     * @return True if the user's age is valid; otherwise, false.
     */
    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return true;
        }

        int minExpectedAge = customUserMinAge == 0 ? defaultUserMinAge : customUserMinAge;
        LocalDate currentDate = LocalDate.now();

        int currentAge = Period.between(dateOfBirth, currentDate).getYears();

        if (currentAge < minExpectedAge) {
            formatMessage(context, minExpectedAge);
            return false;
        }

        return true;
    }

    /**
     * Formats a custom error message for age validation.
     *
     * @param context    The constraint validator context.
     * @param minUserAge The minimum acceptable age.
     */
    private void formatMessage(ConstraintValidatorContext context, int minUserAge) {
        String messageTemplate = context.getDefaultConstraintMessageTemplate();
        String formattedMsg = String.format(messageTemplate, minUserAge);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(formattedMsg)
            .addConstraintViolation();
    }

    @Override
    public void initialize(ValidAge validAge) {
        this.customUserMinAge = validAge.minAge();
    }
}
