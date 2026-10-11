package com.example.investment;

import com.example.investment.model.User;
import com.example.investment.dto.UserRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

class UserApiSafetyTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void userPasswordHashIsNotSerialized() throws Exception {
        User user = new User();
        user.setId(42L);
        user.setUsername("demo");
        user.setPassword("sample-hash-not-a-real-secret");
        user.setBalance(1000.0);

        String json = mapper.writeValueAsString(user);

        assertTrue(json.contains("demo"));
        assertFalse(json.contains("password"));
        assertFalse(json.contains("sample-hash-not-a-real-secret"));
    }

    @Test
    void passwordFieldCanStillBeReadFromIncomingJson() throws Exception {
        User user = mapper.readValue(
            "{\"username\":\"demo\",\"password\":\"example-password\"}", User.class
        );
        assertEquals("example-password", user.getPassword());
    }

    @Test
    void rejectsBlankAndShortRegistrationFields() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            UserRegistrationDto dto = new UserRegistrationDto();
            dto.setUsername("");
            dto.setPassword("short");
            assertFalse(validator.validate(dto).isEmpty());

            dto.setUsername("demo-user");
            dto.setPassword("strong-enough-demo");
            assertTrue(validator.validate(dto).isEmpty());
        }
    }
}
