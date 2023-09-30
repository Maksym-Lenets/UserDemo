package com.example.userdemo.dto;

import com.example.userdemo.annotations.BeforeCurrentDate;
import com.example.userdemo.annotations.ValidAge;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import static com.example.userdemo.constatnt.ErrorMessages.WRONG_EMAIL;
import static com.example.userdemo.constatnt.ErrorMessages.WRONG_PHONE_NUMBER;
import static com.example.userdemo.constatnt.ValidationConstants.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    @Length(min = USER_NAME_MIN_LENGTH, max = USER_NAME_MAX_LENGTH)
    private String firstName;

    @Length(min = USER_NAME_MIN_LENGTH, max = USER_NAME_MAX_LENGTH)
    private String lastName;

    @Length(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH)
    @Pattern(regexp = EMAIL_PATTERN, message = WRONG_EMAIL)
    private String email;

    @ValidAge
    @BeforeCurrentDate
    private LocalDate dateOfBirth;

    @Length(min = ADDRESS_MIN_LENGTH, max = ADDRESS_MAX_LENGTH)
    private String address;

    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = WRONG_PHONE_NUMBER)
    private String phoneNumber;
}
