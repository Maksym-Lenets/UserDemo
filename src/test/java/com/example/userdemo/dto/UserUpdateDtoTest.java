package com.example.userdemo.dto;

import com.example.userdemo.ModelUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "userdemo.validation.userMinAcceptableAge=18")
class UserUpdateDtoTest {

    @Value("${userdemo.validation.userMinAcceptableAge}")
    private int minUserAge;
    private LocalValidatorFactoryBean validator;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        SpringConstraintValidatorFactory springConstraintValidatorFactory =
            new SpringConstraintValidatorFactory(webApplicationContext.getAutowireCapableBeanFactory());
        validator = new LocalValidatorFactoryBean();
        validator.setConstraintValidatorFactory(springConstraintValidatorFactory);
        validator.setApplicationContext(webApplicationContext);
        validator.afterPropertiesSet();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Min", "ValidNameWithMaxLengthEquals30"})
    void validFirstNameTest(String firstName) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setFirstName(firstName);
        assertThat(validator.validate(dto)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Mi", "InvalidLengthOfName31Characters", "", "  "})
    void invalidFirstNameTest(String firstName) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setFirstName(firstName);
        assertThat(validator.validate(dto)).hasSize(1);
    }

    @Test
    void validFirstNameNullTest() {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setFirstName(null);
        assertThat(validator.validate(dto)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Min", "ValidNameWithMaxLengthEquals30"})
    void validLastNameTest(String lastName) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setLastName(lastName);
        assertThat(validator.validate(dto)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Mi", "InvalidLengthOfName31Characters", "", "  "})
    void invalidLastNameTest(String lastName) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setLastName(lastName);
        assertThat(validator.validate(dto)).hasSize(1);
    }

    @Test
    void validDateOfBirthTest() {
        int validAge = minUserAge + 1;
        UserUpdateDto user = ModelUtil.getUserUpdateDto();
        user.setDateOfBirth(LocalDate.now().minusYears(validAge));
        assertThat(validator.validate(user)).hasSize(0);
    }

    @Test
    void invalidDateOfBirthTest() {
        int invalidAge = minUserAge - 1;
        UserUpdateDto user = ModelUtil.getUserUpdateDto();
        user.setDateOfBirth(LocalDate.now().minusYears(invalidAge));
        assertThat(validator.validate(user)).hasSize(1);
    }

    @Test
    void invalidDateOfBirthFromFutureTest() {
        UserUpdateDto user = ModelUtil.getUserUpdateDto();
        user.setDateOfBirth(LocalDate.now().plusYears(1));
        assertThat(validator.validate(user)).hasSize(2);
    }

    @Test
    void validDateOfBirthNullTest() {
        UserUpdateDto user = ModelUtil.getUserUpdateDto();
        user.setDateOfBirth(null);
        assertThat(validator.validate(user)).hasSize(0);
    }

    @Test
    void validLastNameNullTest() {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setLastName(null);
        assertThat(validator.validate(dto)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"john.doe@example.com", "j@e.ua", "thelongestemailaddressihaveeverseen50c@example.com"})
    void validEmailTest(String email) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setEmail(email);
        assertThat(validator.validate(dto)).hasSize(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"thelongestemailaddressihaveeverseen51ch@example.com"})
    void invalidEmailTest(String email) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setEmail(email);
        assertThat(validator.validate(dto)).hasSize(1);
    }

    @Test
    void validEmailNullTest() {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setEmail(null);
        assertThat(validator.validate(dto)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid", "120 Shevchenko Street, Building 123, Apt. 45, Entrance 6, Floor 17, Apt. " +
        "890, Kyiv, Kyiv Oblast, Zip Code 01234, Ukraine"})
    void validAddressTest(String address) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setAddress(address);
        assertThat(validator.validate(dto)).hasSize(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"nope", "121 Shevchenko Street, Building 123, Apt. 45, Entrance 16, Floor 17, Apt. " +
        "890, Kyiv, Kyiv Oblast, Zip Code 01234, Ukraine", "", "  "})
    void invalidAddressTest(String address) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setAddress(address);
        assertThat(validator.validate(dto)).hasSize(1);
    }

    @Test
    void validAddressNullTest() {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setAddress(null);
        assertThat(validator.validate(dto)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"+38 (123) 456 7890", "+38(123)4567890", "+38(123)456-7890", "+381234567890",
        "(123) 456 7890", "(123)4567890", "(123)456-7890", "123 456-7890", "123 456 7890", "1234567890"})
    void validPhoneNumberTest(String phoneNumber) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setPhoneNumber(phoneNumber);
        assertThat(validator.validate(dto)).hasSize(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234567", "+38 (1233) 45 7890", "+38(1203)567890", "+38(123)456-7A90",
        "(1263) 56 7890", "(1423)456890", "(1253)456-790", "12356-7890", "12345678901"})
    void invalidPhoneNumberTest(String phoneNumber) {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setPhoneNumber(phoneNumber);
        assertThat(validator.validate(dto)).hasSize(1);
    }

    @Test
    void validPhoneNumberNullTest() {
        UserUpdateDto dto = ModelUtil.getUserUpdateDto();
        dto.setPhoneNumber(null);
        assertThat(validator.validate(dto)).isEmpty();
    }

}