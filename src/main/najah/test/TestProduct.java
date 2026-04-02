package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import main.najah.code.Product;

@DisplayName("Product Tests")
@Execution(ExecutionMode.CONCURRENT)
public class TestProduct {

    Product p;

    @BeforeEach
    void setUp() {
        p = new Product("Laptop", 1000.0);
        System.out.println("Product setup complete");
    }

    @Test
    @DisplayName("Create product with valid name and price")
    void testCreateProductValid() {
        assertEquals("Laptop", p.getName());
        assertEquals(1000.0, p.getPrice());
        assertEquals(0.0, p.getDiscount());
    }

    @Test
    @DisplayName("Create product with negative price should throw exception")
    void testCreateProductNegativePrice() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Product("Phone", -100.0));
        assertEquals("Price must be non-negative", ex.getMessage());
    }

    @Test
    @DisplayName("Apply zero discount should keep same final price")
    void testApplyZeroDiscount() {
        p.applyDiscount(0);

        assertEquals(0.0, p.getDiscount());
        assertEquals(1000.0, p.getFinalPrice());
    }

    @Test
    @DisplayName("Apply maximum allowed discount")
    void testApplyMaximumDiscount() {
        p.applyDiscount(50);

        assertEquals(50.0, p.getDiscount());
        assertEquals(500.0, p.getFinalPrice());
    }
    
    @Test
    @DisplayName("Apply valid discount")
    void testApplyValidDiscount() {
        p.applyDiscount(20);

        assertEquals(20.0, p.getDiscount());
        assertEquals(800.0, p.getFinalPrice());
    }

    @Test
    @DisplayName("Apply discount below zero should throw exception")
    void testApplyNegativeDiscount() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> p.applyDiscount(-5));
        assertEquals("Invalid discount", ex.getMessage());
    }

    @Test
    @DisplayName("Apply discount above fifty should throw exception")
    void testApplyDiscountAboveFifty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> p.applyDiscount(60));
        assertEquals("Invalid discount", ex.getMessage());
    }

    @Test
    @DisplayName("Get final price without discount")
    void testGetFinalPriceWithoutDiscount() {
        assertEquals(1000.0, p.getFinalPrice());
        assertTrue(p.getFinalPrice() > 0);
    }

    @Test
    @DisplayName("Get final price after discount")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testGetFinalPriceAfterDiscount() {
        p.applyDiscount(10);

        assertEquals(900.0, p.getFinalPrice());
        assertNotEquals(1000.0, p.getFinalPrice());
    }

    @Test
    @Disabled("Intentionally failing test. Fix by changing expected final price to 900.")
    @DisplayName("Intentional failing product test")
    void intentionallyFailingProductTest() {
        p.applyDiscount(10);
        assertEquals(850.0, p.getFinalPrice());
    }
    @ParameterizedTest
    @CsvSource({
        "Cola, 10.0",
        "Milk, 25.0",
        "Bread, 5.0"
    })
    @DisplayName("Parameterized test for creating product with valid name and price")
    void testCreateProductParameterized(String name, double price) {
        Product product = new Product(name, price);

        assertAll(
            () -> assertEquals(name, product.getName()),
            () -> assertEquals(price, product.getPrice()),
            () -> assertEquals(0.0, product.getDiscount())
        );
    }
    
}