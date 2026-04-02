package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import main.najah.code.UserService;

@DisplayName("UserService Tests")
public class TestUserService {

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        System.out.println("UserService setup complete");
    }

    @Test
    @DisplayName("Valid and invalid email cases")
    void testValidEmail() {
        assertTrue(userService.isValidEmail("test@gmail.com"));
        assertFalse(userService.isValidEmail("testgmailcom"));
    }

    @Test
    @DisplayName("Null email should return false")
    void testNullEmail() {
        assertFalse(userService.isValidEmail(null));
    }

    @Test
    @DisplayName("Email without at sign should return false")
    void testEmailWithoutAt() {
        assertFalse(userService.isValidEmail("testgmail.com"));
    }

    @Test
    @DisplayName("Email without dot should return false")
    void testEmailWithoutDot() {
        assertFalse(userService.isValidEmail("test@gmailcom"));
    }

    @Test
    @DisplayName("Correct username and password should authenticate")
    void testAuthenticateValid() {
        assertTrue(userService.authenticate("admin", "1234"));
        assertFalse(userService.authenticate("admin", "wrong"));
    }

    @Test
    @DisplayName("Wrong username should fail authentication")
    void testAuthenticateWrongUsername() {
        assertFalse(userService.authenticate("user", "1234"));
    }

    @Test
    @DisplayName("Wrong password should fail authentication")
    void testAuthenticateWrongPassword() {
        assertFalse(userService.authenticate("admin", "0000"));
    }

    @Test
    @DisplayName("Wrong username and password should fail authentication")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testAuthenticateWrongBoth() {
        assertFalse(userService.authenticate("user", "wrong"));
    }

    @Test
    @Disabled("Intentionally failing test. Fix by changing expected result to true.")
    @DisplayName("Intentional failing authentication test")
    void intentionallyFailingUserServiceTest() {
        assertFalse(userService.authenticate("admin", "1234"));
    }
    @ParameterizedTest
    @CsvSource({
        "test@gmail.com, true",
        "testgmail.com, false",
        "user@yahoo.com, true",
        "'', false"
    })
    @DisplayName("Parameterized test for email validation")
    void testIsValidEmailParameterized(String email, boolean expected) {
        assertEquals(expected, userService.isValidEmail(email));
    }
}