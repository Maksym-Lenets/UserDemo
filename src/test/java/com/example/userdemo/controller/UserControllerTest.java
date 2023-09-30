package com.example.userdemo.controller;

import com.example.userdemo.ModelUtil;
import com.example.userdemo.dto.UserUpdateDto;
import com.example.userdemo.exception.NotFoundException;
import com.example.userdemo.model.User;
import com.example.userdemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.example.userdemo.ModelUtil.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void registerTest() {
        User newUser = ModelUtil.getNewUser();
        var jsonNewUser = asJsonString(newUser);
        User savedUser = ModelUtil.getSavedUser();
        var jsonExpectedUser = asJsonString(savedUser);

        when(userService.create(newUser)).thenReturn(savedUser);

        mockMvc.perform(post("/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonNewUser))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(jsonExpectedUser));

        verify(userService).create(newUser);
    }

    @Test
    @SneakyThrows
    void registerBadRequestTest() {
        var jsonInvalidUser = asJsonString(getInvalidUser());

        mockMvc.perform(post("/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonInvalidUser))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, never()).create(any(User.class));
    }

    @Test
    @SneakyThrows
    void updateFullyTest() {
        long userId = 1L;
        User updatedUser = ModelUtil.getNewUser();
        var jsonUpdateUser = asJsonString(updatedUser);
        User savedUser = ModelUtil.getSavedUser();
        var savedJsonUser = asJsonString(savedUser);

        when(userService.update(userId, updatedUser)).thenReturn(savedUser);

        mockMvc.perform(put("/v1/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonUpdateUser))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(savedJsonUser));

        verify(userService).update(userId, updatedUser);
    }

    @Test
    @SneakyThrows
    void updateFullyBadRequestTest() {
        long userId = 1L;
        var jsonInvalidUser = asJsonString(getInvalidUser());

        mockMvc.perform(put("/v1/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonInvalidUser))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, never()).update(any(), any(User.class));
    }

    @Test
    @SneakyThrows
    void updateFullyNotFoundTest() {
        long userId = 100500L;
        User updatedUser = ModelUtil.getNewUser();
        var jsonUpdateUser = asJsonString(updatedUser);
        var errorMessage = "some error";

        when(userService.update(userId, updatedUser)).thenThrow(new NotFoundException(errorMessage));

        mockMvc.perform(put("/v1/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonUpdateUser))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService).update(userId, updatedUser);
    }

    @Test
    @SneakyThrows
    void updatePartiallyTest() {
        long userId = 1L;
        UserUpdateDto userDto = getUserUpdateDto();
        var jsonUpdateDto = asJsonString(userDto);
        User savedUser = ModelUtil.getSavedUser();
        var savedJsonUser = asJsonString(savedUser);

        when(userService.update(userId, userDto)).thenReturn(getSavedUser());

        mockMvc.perform(patch("/v1/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonUpdateDto))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(savedJsonUser));

        verify(userService).update(userId, userDto);
    }

    @Test
    @SneakyThrows
    void updatePartiallyBadRequestTest() {
        long userId = 1L;
        var jsonInvalidUserDto = asJsonString(getInvalidUserUpdateDto());

        mockMvc.perform(patch("/v1/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonInvalidUserDto))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, never()).update(any(), any(UserUpdateDto.class));

    }

    @Test
    @SneakyThrows
    void updatePartiallyNotFoundTest() {
        long userId = 100500L;
        UserUpdateDto userDto = getUserUpdateDto();
        var jsonUpdateDto = asJsonString(userDto);
        var errorMessage = "some error";

        when(userService.update(userId, userDto)).thenThrow(new NotFoundException(errorMessage));

        mockMvc.perform(patch("/v1/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonUpdateDto))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService).update(userId, userDto);
    }

    @Test
    @SneakyThrows
    void deleteTest() {
        long userId = 1L;

        mockMvc.perform(delete("/v1/users/" + userId))
            .andExpect(status().isOk());

        verify(userService).delete(userId);
    }

    @Test
    @SneakyThrows
    void deleteNotFoundTest() {
        long userId = 100500L;
        var errorMessage = "some error";

        doThrow(new NotFoundException(errorMessage)).when(userService).delete(userId);

        mockMvc.perform(delete("/v1/users/" + userId))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService).delete(userId);
    }

    @Test
    @SneakyThrows
    void getByDateOfBirthTest() {
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);
        var fromValue = "2023-01-01";
        var toValue = "2023-12-31";
        List<User> users = List.of(getSavedUser(), getSavedUser());
        var expectedResult = asJsonString(users);

        when(userService.getByDateOfBirth(from, to)).thenReturn(users);

        mockMvc.perform(get("/v1/users/byDateOfBirth")
            .param("from", fromValue)
            .param("to", toValue))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedResult));

        verify(userService).getByDateOfBirth(from, to);
    }

    @Test
    @SneakyThrows
    void getByDateOfBirthBadRequestTest() {
        LocalDate from = LocalDate.of(2023, 12, 31);
        LocalDate to = LocalDate.of(2023, 1, 1);
        var fromValue = "2023-12-31";
        var toValue = "2023-01-01";
        var errorMessage = "some error";

        when(userService.getByDateOfBirth(from, to)).thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(get("/v1/users/byDateOfBirth")
            .param("from", fromValue)
            .param("to", toValue))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService).getByDateOfBirth(from, to);
    }

    @Test
    @SneakyThrows
    void getAll() {
        List<User> users = List.of(getSavedUser(), getSavedUser());
        var expectedResult = asJsonString(users);

        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get("/v1/users/all"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedResult));

        verify(userService).getAll();
    }

    @SneakyThrows
    private String asJsonString(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }
}