package com.example.userdemo.controller;

import com.example.userdemo.dto.UserUpdateDto;
import com.example.userdemo.model.User;
import com.example.userdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 * User Controller for managing user-related operations.
 *
 * This controller provides endpoints for creating, updating, deleting, and
 * retrieving user information.
 *
 * @RestController: Indicates that this class is a RESTful controller.
 * @RequestMapping: Specifies the base URL for this controller's endpoints.
 * @RequiredArgsConstructor: Automatically generates a constructor with required
 *                           fields.
 * @Tag: Provides OpenAPI (Swagger) documentation for this controller.
 */

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API for administrator to support menu items")
public class UserController {
    private final UserService userService;

    /**
     * Add new User.
     *
     * @param user The user object to be created. Must be a valid User entity.
     * @return ResponseEntity containing the saved User.
     */
    @Operation(
        summary = "Add new User",
        description = "returns saved User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Exception during new User validating")
    })
    @PostMapping
    public ResponseEntity<User> register(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    /**
     * Complete update of an existing User.
     *
     * @param id   The ID of the user to be updated.
     * @param user The user object with updated information. Must be a valid User
     *             entity.
     * @return ResponseEntity containing the updated User.
     */
    @Operation(
        summary = "Complete update of an existing User",
        description = "returns updated User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Exception during new User validating"),
        @ApiResponse(responseCode = "404", description = "User not found by provided ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateFully(@PathVariable("id") Long id,
        @RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    /**
     * Partial update of an existing User.
     *
     * @param id   The ID of the user to be updated.
     * @param user The UserUpdateDto object with partially updated information. Must
     *             be a valid UserUpdateDto.
     * @return ResponseEntity containing the updated User.
     */
    @Operation(
        summary = "Partial update of an existing User",
        description = "returns updated User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Exception during UserUpdateDto validating"),
        @ApiResponse(responseCode = "404", description = "User not found by provided ID")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePartially(@PathVariable("id") Long id,
        @RequestBody @Valid UserUpdateDto user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    /**
     * Delete an existing User by ID.
     *
     * @param id The ID of the user to be deleted.
     * @return ResponseEntity indicating a successful deletion.
     */
    @Operation(
        summary = "Delete an existing User by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User created successfully"),
        @ApiResponse(responseCode = "404", description = "User not found by provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Get users by date of birth a time range.
     *
     * @param from The start date of the range.
     * @param to   The end date of the range.
     * @return ResponseEntity containing a list of users with date of birth in the
     *         specified time frame.
     */
    @Operation(
        summary = "Get users by date of birth in a range",
        description = "returns a list of users with a date of birth in the specified time frame")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid time frame")
    })
    @GetMapping("/byDateOfBirth")
    public ResponseEntity<List<User>> getByDateOfBirth(@RequestParam LocalDate from,
        @RequestParam LocalDate to) {
        return ResponseEntity.ok(userService.getByDateOfBirth(from, to));
    }

    /**
     * Get all users.
     *
     * @return ResponseEntity containing a list of all saved Users.
     */
    @Operation(
        summary = "Get all users",
        description = "returns list of saved Users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}
