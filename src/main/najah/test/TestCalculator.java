package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Calculator;

@DisplayName("Calculator Tests")
@TestMethodOrder(OrderAnnotation.class)
public class TestCalculator {

    Calculator calc;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting Calculator tests...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finished Calculator tests...");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("Setup complete");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished");
    }

    @Test
    @Order(1)
    @DisplayName("Add multiple valid numbers")
    void testAddMultipleNumbers() {
        int result = calc.add(1, 2, 3, 4);
        assertEquals(10, result);
        assertNotEquals(9, result);
    }

    @Test
    @Order(2)
    @DisplayName("Add with no numbers should return zero")
    void testAddNoNumbers() {
        int result = calc.add();
        assertEquals(0, result);
        assertTrue(result == 0);
    }

    @Test
    @Order(3)
    @DisplayName("Divide valid values")
    void testDivideValid() {
        assertEquals(5, calc.divide(10, 2));
        assertEquals(3, calc.divide(9, 3));
    }

    @Test
    @Order(4)
    @DisplayName("Divide by zero should throw ArithmeticException")
    void testDivideByZero() {
        ArithmeticException ex = assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
        assertEquals("Cannot divide by zero", ex.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Factorial valid values")
    void testFactorialValid() {
        assertEquals(1, calc.factorial(0));
        assertEquals(1, calc.factorial(1));
        assertEquals(120, calc.factorial(5));
    }

    @ParameterizedTest
    @CsvSource({
        "0,1",
        "1,1",
        "5,120"
    })
    @Order(6)
    @DisplayName("Factorial parameterized test")
    void testFactorialParameterized(int input, int expected) {
        assertEquals(expected, calc.factorial(input));
    }

    @Test
    @Order(7)
    @DisplayName("Factorial with negative input should throw exception")
    void testFactorialNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> calc.factorial(-3));
        assertEquals("Negative input", ex.getMessage());
    }

    @Test
    @Order(8)
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Factorial completes within timeout")
    void testFactorialTimeout() {
        int result = calc.factorial(6);
        assertEquals(720, result);
        assertTrue(result > 0);
    }

    @Test
    @Order(9)
    @Disabled("Intentionally failing test. Fix by changing the expected value to 6.")
    @DisplayName("Intentional failing test for demonstration")
    void intentionallyFailingTest() {
        assertEquals(7, calc.add(1, 2, 3));
    }
    
    @Test
    @Order(10)
    @DisplayName("Add positive and negative numbers")
    void testAddPositiveAndNegativeNumbers() {
        int result = calc.add(10, -3, -2);

        assertEquals(5, result);
        assertNotEquals(0, result);
    }
    
    
    @Test
    @Order(11)
    @DisplayName("Divide negative numbers")
    void testDivideNegativeNumbers() {
        assertEquals(-5, calc.divide(-10, 2));
        assertEquals(5, calc.divide(-10, -2));
    } 
    
    @Test
    @Order(12)
    @DisplayName("Factorial of two should return two")
    void testFactorialTwo() {
        int result = calc.factorial(2);

        assertEquals(2, result);
        assertTrue(result > 0);
    }
    @Test
    @Order(13)
    @DisplayName("Add single number should return same number")
    void testAddSingleNumber() {
        int result = calc.add(7);

        assertEquals(7, result);
        assertNotEquals(0, result);
    }
    
    
}