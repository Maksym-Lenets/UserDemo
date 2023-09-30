package com.example.userdemo.service;

import com.example.userdemo.dto.UserUpdateDto;
import com.example.userdemo.model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * The UserService interface that provides methods for managing user data.
 */
public interface UserService {
    /**
     * Creates a new user.
     *
     * @param user The user entity to be created.
     * @return The created user entity.
     */
    User create(User user);

    /**
     * Updates all data in an existing user with the given ID.
     *
     * @param id   The ID of the user to be updated.
     * @param user The user entity with updated information.
     * @return The updated user entity.
     */
    User update(Long id, User user);

    /**
     * Partially updates an existing user with the given ID using the provided
     * UserUpdateDto.
     *
     * @param id      The ID of the user to be updated.
     * @param userDto The UserUpdateDto containing the updated user information.
     * @return The updated user entity.
     */
    User update(Long id, UserUpdateDto userDto);

    /**
     * Deletes a user with the given ID.
     *
     * @param userId The ID of the user to be deleted.
     */
    void delete(Long userId);

    /**
     * Retrieves a list of users with a date of birth within the specified date
     * range.
     *
     * @param from The start date of the date range (inclusive).
     * @param to   The end date of the date range (inclusive).
     * @return A list of users with date of birth within the specified range.
     */
    List<User> getByDateOfBirth(LocalDate from, LocalDate to);

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all users.
     */
    List<User> getAll();
}
