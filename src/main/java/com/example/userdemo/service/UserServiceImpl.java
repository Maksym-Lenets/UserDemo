package com.example.userdemo.service;

import com.example.userdemo.dto.UserUpdateDto;
import com.example.userdemo.exception.NotFoundException;
import com.example.userdemo.model.User;
import com.example.userdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.example.userdemo.constatnt.ErrorMessages.USER_NOT_FOUND_BY_ID;
import static com.example.userdemo.constatnt.ErrorMessages.WRONG_TIME_PERIOD;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User create(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(Long id, User user) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_BY_ID + id));
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(Long id, UserUpdateDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_BY_ID + id));
        updateWithDto(user, userDto);
        return user;
    }

    @Override
    public void delete(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_BY_ID + userId));
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getByDateOfBirth(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException(WRONG_TIME_PERIOD);
        }
        return userRepository.findByDateOfBirthBetween(from, to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    private void updateWithDto(User user, UserUpdateDto updatedUser) {
        if (isNotBlank(updatedUser.getFirstName())) {
            user.setFirstName(updatedUser.getFirstName());
        }
        if (isNotBlank(updatedUser.getLastName())) {
            user.setLastName(updatedUser.getLastName());
        }
        if (isNotBlank(updatedUser.getEmail())) {
            user.setEmail(updatedUser.getEmail());
        }
        if (Objects.nonNull(updatedUser.getDateOfBirth())) {
            user.setDateOfBirth(updatedUser.getDateOfBirth());
        }
        if (isNotBlank(updatedUser.getAddress())) {
            user.setAddress(updatedUser.getAddress());
        }
        if (isNotBlank(updatedUser.getPhoneNumber())) {
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }
    }
}
