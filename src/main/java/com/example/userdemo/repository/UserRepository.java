package com.example.userdemo.repository;

import com.example.userdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing user entities in the application. Provides
 * methods for querying and managing user data in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a list of users whose date of birth falls within the specified date
     * range.
     *
     * @param from The start date of the date range (inclusive).
     * @param to   The end date of the date range (inclusive).
     * @return A list of users with date of birth within the specified range.
     */
    List<User> findByDateOfBirthBetween(LocalDate from, LocalDate to);
}
