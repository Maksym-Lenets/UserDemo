package com.example.userdemo.service;

import com.example.userdemo.ModelUtil;
import com.example.userdemo.constatnt.ErrorMessages;
import com.example.userdemo.dto.UserUpdateDto;
import com.example.userdemo.exception.NotFoundException;
import com.example.userdemo.model.User;
import com.example.userdemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.userdemo.ModelUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    void createTest() {
        User newUser = getNewUser();
        User savedUser = getSavedUser();

        when(userRepository.save(newUser)).thenReturn(savedUser);

        var result = userService.create(newUser);

        verify(userRepository).save(newUser);
        assertEquals(savedUser, result);
    }

    @Test
    void fullUpdateTest() {
        long id = 1L;
        User updatedUser = getUpdatedUser();
        User savedUser = getSavedUser();
        User expected = getUpdatedUser();
        expected.setId(savedUser.getId());

        when(userRepository.findById(id)).thenReturn(Optional.of(savedUser));
        when(userRepository.save(expected)).thenReturn(expected);

        var result = userService.update(id, updatedUser);

        verify(userRepository).findById(id);
        verify(userRepository).save(expected);
        assertEquals(expected, result);
    }

    @Test
    void fullUpdateNotExistingUserThrowsExceptionTestTest() {
        long id = 100500L;
        User updatedUser = getUpdatedUser();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        var result = assertThrows(NotFoundException.class, () -> userService.update(id, updatedUser));

        verify(userRepository).findById(id);
        verify(userRepository, never()).save(any(User.class));
        assertEquals(ErrorMessages.USER_NOT_FOUND_BY_ID + id, result.getMessage());
    }

    @ParameterizedTest
    @MethodSource("testUserUpdateDto")
    void partialUpdateTest(UserUpdateDto updateDto, User expected) {
        long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(getSavedUser()));

        var result = userService.update(id, updateDto);

        assertEquals(expected, result);
    }

    @Test
    void partialUpdateNotExistingUserThrowsExceptionTestTest() {
        long id = 100500L;
        UserUpdateDto updatedUser = getUserUpdateDto();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        var result = assertThrows(NotFoundException.class, () -> userService.update(id, updatedUser));

        verify(userRepository).findById(id);
        assertEquals(ErrorMessages.USER_NOT_FOUND_BY_ID + id, result.getMessage());
    }

    @Test
    void deleteTest() {
        long id = 1L;
        User savedUser = getSavedUser();

        when(userRepository.findById(id)).thenReturn(Optional.of(savedUser));

        userService.delete(id);

        verify(userRepository).findById(id);
        verify(userRepository).delete(savedUser);
    }

    @Test
    void deleteNotExistingUserThrowsExceptionTest() {
        long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class, () -> userService.delete(id));

        verify(userRepository).findById(id);
        verify(userRepository, never()).delete(any(User.class));
        assertEquals(ErrorMessages.USER_NOT_FOUND_BY_ID + id, result.getMessage());
    }

    @Test
    void getByDateOfBirthTest() {
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);
        List<User> savedUsers = List.of(getSavedUser(), getSavedUser());

        when(userRepository.findByDateOfBirthBetween(from, to)).thenReturn(savedUsers);

        var result = userService.getByDateOfBirth(from, to);

        verify(userRepository).findByDateOfBirthBetween(from, to);
        assertEquals(savedUsers, result);
    }

    @Test
    void getByDateOfBirthInvalidDatesThrowsExceptionTest() {
        LocalDate from = LocalDate.of(2023, 12, 31);
        LocalDate to = LocalDate.of(2023, 1, 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.getByDateOfBirth(from, to));

        verify(userRepository, never()).findByDateOfBirthBetween(from, to);
        assertEquals(ErrorMessages.WRONG_TIME_PERIOD, exception.getMessage());
    }

    @Test
    void getAllTest() {
        List<User> savedUsers = List.of(getSavedUser(), getSavedUser());

        when(userRepository.findAll()).thenReturn(savedUsers);

        var result = userService.getAll();

        verify(userRepository).findAll();
        assertEquals(savedUsers, result);
    }

    private static Stream<Arguments> testUserUpdateDto() {
        return Stream.of(
            getFullyUpdatedUser(),
            ModelUtil.getUserWithUpdatedFirstName(),
            ModelUtil.getUserWithUpdatedLastName(),
            ModelUtil.getUserWithUpdatedEmail(),
            ModelUtil.getUserWithUpdatedDateOfBirth(),
            ModelUtil.getUserWithUpdatedAddress(),
            ModelUtil.getUserUpdateDtoWithNewPhoneNumber());
    }
}