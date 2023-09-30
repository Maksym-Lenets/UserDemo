package com.example.userdemo;

import com.example.userdemo.dto.UserUpdateDto;
import com.example.userdemo.model.User;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;

public class ModelUtil {
    public static User getNewUser() {
        return User.builder()
            .firstName("Test_Name")
            .lastName("Test_LastName")
            .email("test@example.com")
            .dateOfBirth(LocalDate.of(1990, 1, 1))
            .address("123 test St")
            .phoneNumber("+1234567890")
            .build();
    }

    public static User getSavedUser() {
        return User.builder()
            .id(1L)
            .firstName("Test_Name")
            .lastName("Test_LastName")
            .email("test@example.com")
            .dateOfBirth(LocalDate.of(1990, 1, 1))
            .address("123 test St")
            .phoneNumber("+1234567890")
            .build();
    }

    public static User getUpdatedUser() {
        return User.builder()
            .firstName("New_Name")
            .lastName("New_LastName")
            .email("New@example.com")
            .dateOfBirth(LocalDate.of(1992, 1, 1))
            .address("New test St")
            .phoneNumber("+9087654321")
            .build();
    }

    public static User getInvalidUser() {
        return User.builder()
            .firstName("Te")
            .lastName("Te")
            .email("testexamplecom")
            .dateOfBirth(LocalDate.of(1990, 1, 1))
            .address("12")
            .phoneNumber("7890")
            .build();
    }

    public static UserUpdateDto getUserUpdateDto() {
        return UserUpdateDto.builder()
            .firstName("Test_Name")
            .lastName("Test_LastName")
            .email("test@example.com")
            .dateOfBirth(LocalDate.of(1990, 1, 1))
            .address("123 test St")
            .phoneNumber("+1234567890")
            .build();
    }

    public static UserUpdateDto getInvalidUserUpdateDto() {
        return UserUpdateDto.builder()
            .firstName("Te")
            .lastName("Te")
            .email("testexamplecom")
            .dateOfBirth(LocalDate.of(1990, 1, 1))
            .address("12")
            .phoneNumber("+1234")
            .build();
    }

    public static Arguments getFullyUpdatedUser() {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .firstName("NEW_Name")
            .lastName("NEW_LastName")
            .email("NEW@example.com")
            .dateOfBirth(LocalDate.of(1993, 1, 1))
            .address("NEW 123 test St")
            .phoneNumber("+380661558971")
            .build();
        User user = User.builder()
            .id(1L)
            .firstName("NEW_Name")
            .lastName("NEW_LastName")
            .email("NEW@example.com")
            .dateOfBirth(LocalDate.of(1993, 1, 1))
            .address("NEW 123 test St")
            .phoneNumber("+380661558971")
            .build();
        return Arguments.arguments(userUpdateDto, user);
    }

    public static Arguments getUserWithUpdatedFirstName() {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .firstName("NEW_Name")
            .build();
        User user = getSavedUser();
        user.setFirstName(userUpdateDto.getFirstName());
        return Arguments.arguments(userUpdateDto, user);
    }

    public static Arguments getUserWithUpdatedLastName() {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .lastName("NEW_LastName")
            .build();
        User user = getSavedUser();
        user.setLastName(userUpdateDto.getLastName());
        return Arguments.arguments(userUpdateDto, user);
    }

    public static Arguments getUserWithUpdatedEmail() {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .email("NEW@example.com")
            .build();
        User user = getSavedUser();
        user.setEmail(userUpdateDto.getEmail());
        return Arguments.arguments(userUpdateDto, user);
    }

    public static Arguments getUserWithUpdatedDateOfBirth() {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .dateOfBirth(LocalDate.of(1993, 1, 1))
            .build();
        User user = getSavedUser();
        user.setDateOfBirth(userUpdateDto.getDateOfBirth());
        return Arguments.arguments(userUpdateDto, user);
    }

    public static Arguments getUserWithUpdatedAddress() {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .address("NEW 123 test St")
            .build();
        User user = getSavedUser();
        user.setAddress(userUpdateDto.getAddress());
        return Arguments.arguments(userUpdateDto, user);
    }

    public static Arguments getUserUpdateDtoWithNewPhoneNumber() {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .phoneNumber("+380661558971")
            .build();
        User user = getSavedUser();
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        return Arguments.arguments(userUpdateDto, user);
    }

}
