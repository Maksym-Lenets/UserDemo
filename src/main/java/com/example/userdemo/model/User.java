package com.example.userdemo.model;

import com.example.userdemo.annotations.BeforeCurrentDate;
import com.example.userdemo.annotations.ValidAge;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

/**
 * Represents a user entity in the application. This entity is used for storing
 * user information in a database.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TABLE")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 30)
    @NotBlank
    @Length(min = USER_NAME_MIN_LENGTH, max = USER_NAME_MAX_LENGTH)
    private String firstName;

    @Column(nullable = false, length = 30)
    @NotBlank
    @Length(min = USER_NAME_MIN_LENGTH, max = USER_NAME_MAX_LENGTH)
    private String lastName;

    @Column(unique = true, nullable = false, length = 50)
    @NotBlank
    @Length(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH)
    @Pattern(regexp = EMAIL_PATTERN, message = WRONG_EMAIL)
    private String email;

    @Column(nullable = false)
    @NotNull
    @BeforeCurrentDate
    @ValidAge
    private LocalDate dateOfBirth;

    @Length(min = ADDRESS_MIN_LENGTH, max = ADDRESS_MAX_LENGTH)
    private String address;

    @Pattern(regexp = PHONE_NUMBER_PATTERN, message = WRONG_PHONE_NUMBER)
    private String phoneNumber;
}
