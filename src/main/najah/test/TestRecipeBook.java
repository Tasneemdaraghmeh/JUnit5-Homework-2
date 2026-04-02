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

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import main.najah.code.RecipeException;

@DisplayName("RecipeBook Tests")
public class TestRecipeBook {

    RecipeBook recipeBook;
    Recipe recipe1;
    Recipe recipe2;

    @BeforeEach
    void setUp() throws RecipeException {
        recipeBook = new RecipeBook();

        recipe1 = new Recipe();
        recipe1.setName("Espresso");
        recipe1.setPrice("10");
        recipe1.setAmtCoffee("2");
        recipe1.setAmtMilk("1");
        recipe1.setAmtSugar("1");
        recipe1.setAmtChocolate("0");

        recipe2 = new Recipe();
        recipe2.setName("Latte");
        recipe2.setPrice("15");
        recipe2.setAmtCoffee("2");
        recipe2.setAmtMilk("2");
        recipe2.setAmtSugar("1");
        recipe2.setAmtChocolate("1");

        System.out.println("RecipeBook setup complete");
    }

    @Test
    @DisplayName("Add valid recipe successfully")
    void testAddRecipeValid() {
        boolean added = recipeBook.addRecipe(recipe1);

        assertTrue(added);
        assertEquals("Espresso", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Add duplicate recipe should return false")
    void testAddDuplicateRecipe() {
        recipeBook.addRecipe(recipe1);
        boolean addedAgain = recipeBook.addRecipe(recipe1);

        assertFalse(addedAgain);
    }

    @Test
    @DisplayName("Add recipes until recipe book is full")
    void testAddRecipesUntilFull() throws RecipeException {
        Recipe recipe3 = new Recipe();
        recipe3.setName("Mocha");
        recipe3.setPrice("20");

        Recipe recipe4 = new Recipe();
        recipe4.setName("Cappuccino");
        recipe4.setPrice("18");

        Recipe recipe5 = new Recipe();
        recipe5.setName("Americano");
        recipe5.setPrice("12");

        assertTrue(recipeBook.addRecipe(recipe1));
        assertTrue(recipeBook.addRecipe(recipe2));
        assertTrue(recipeBook.addRecipe(recipe3));
        assertTrue(recipeBook.addRecipe(recipe4));
        assertFalse(recipeBook.addRecipe(recipe5));
    }

    @Test
    @DisplayName("Delete existing recipe should return recipe name")
    void testDeleteRecipeValid() {
        recipeBook.addRecipe(recipe1);

        String deletedName = recipeBook.deleteRecipe(0);

        assertEquals("Espresso", deletedName);
        assertEquals("", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Delete empty recipe slot should return null")
    void testDeleteRecipeFromEmptySlot() {
        String deletedName = recipeBook.deleteRecipe(0);

        assertNull(deletedName);
    }

    @Test
    @DisplayName("Edit existing recipe should return old recipe name")
    void testEditRecipeValid() {
        recipeBook.addRecipe(recipe1);

        String oldName = recipeBook.editRecipe(0, recipe2);

        assertEquals("Espresso", oldName);
        assertEquals("", recipeBook.getRecipes()[0].getName());
    }

    @Test
    @DisplayName("Edit empty recipe slot should return null")
    void testEditRecipeEmptySlot() {
        String result = recipeBook.editRecipe(0, recipe2);

        assertNull(result);
    }

    @Test
    @DisplayName("Recipe price with invalid text should throw RecipeException")
    void testRecipeInvalidPrice() {
        Recipe recipe = new Recipe();

        RecipeException ex = assertThrows(RecipeException.class, () -> recipe.setPrice("abc"));
        assertEquals("Price must be a positive integer", ex.getMessage());
    }

    @Test
    @DisplayName("Recipe coffee amount with negative value should throw RecipeException")
    void testRecipeNegativeCoffeeAmount() {
        Recipe recipe = new Recipe();

        RecipeException ex = assertThrows(RecipeException.class, () -> recipe.setAmtCoffee("-1"));
        assertEquals("Units of coffee must be a positive integer", ex.getMessage());
    }

    @Test
    @DisplayName("Recipe milk amount with invalid text should throw RecipeException")
    void testRecipeInvalidMilkAmount() {
        Recipe recipe = new Recipe();

        RecipeException ex = assertThrows(RecipeException.class, () -> recipe.setAmtMilk("milk"));
        assertEquals("Units of milk must be a positive integer", ex.getMessage());
    }

    @Test
    @DisplayName("Recipe getters return correct values")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testRecipeGetters() {
        assertEquals("Espresso", recipe1.getName());
        assertEquals(10, recipe1.getPrice());
        assertEquals(2, recipe1.getAmtCoffee());
        assertEquals(1, recipe1.getAmtMilk());
        assertEquals(1, recipe1.getAmtSugar());
        assertEquals(0, recipe1.getAmtChocolate());
    }

    @Test
    @DisplayName("Recipe sugar amount valid value")
    void testRecipeSugarValid() throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setAmtSugar("3");

        assertEquals(3, recipe.getAmtSugar());
    }

    @Test
    @DisplayName("Recipe sugar invalid text should throw exception")
    void testRecipeSugarInvalid() {
        Recipe recipe = new Recipe();

        RecipeException ex = assertThrows(RecipeException.class, () -> recipe.setAmtSugar("abc"));
        assertEquals("Units of sugar must be a positive integer", ex.getMessage());
    }

    @Test
    @DisplayName("Recipe chocolate valid value")
    void testRecipeChocolateValid() throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setAmtChocolate("4");

        assertEquals(4, recipe.getAmtChocolate());
    }

    @Test
    @DisplayName("Recipe chocolate negative value should throw exception")
    void testRecipeChocolateNegative() {
        Recipe recipe = new Recipe();

        RecipeException ex = assertThrows(RecipeException.class, () -> recipe.setAmtChocolate("-2"));
        assertEquals("Units of chocolate must be a positive integer", ex.getMessage());
    }

    @Test
    @DisplayName("Recipe set name with null should keep old name")
    void testSetNameNull() {
        Recipe recipe = new Recipe();
        recipe.setName("Mocha");
        recipe.setName(null);

        assertEquals("Mocha", recipe.getName());
    }

    @Test
    @DisplayName("Recipe toString returns recipe name")
    void testRecipeToString() {
        Recipe recipe = new Recipe();
        recipe.setName("Latte");

        assertEquals("Latte", recipe.toString());
    }

    @Test
    @DisplayName("Recipe equality with same name")
    void testRecipeEquals() {
        Recipe r1 = new Recipe();
        r1.setName("Mocha");

        Recipe r2 = new Recipe();
        r2.setName("Mocha");

        assertTrue(r1.equals(r2));
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("Recipe inequality with different names")
    void testRecipeNotEquals() {
        Recipe r1 = new Recipe();
        r1.setName("Mocha");

        Recipe r2 = new Recipe();
        r2.setName("Latte");

        assertFalse(r1.equals(r2));
    }
    
    @Test
    @DisplayName("Recipe equals with itself should return true")
    void testRecipeEqualsItself() {
        Recipe recipe = new Recipe();
        recipe.setName("Mocha");

        assertTrue(recipe.equals(recipe));
    }
    
    
    @Test
    @DisplayName("Recipe sugar invalid text should throw exception again")
    void testRecipeSugarInvalidAgain() {
        Recipe recipe = new Recipe();

        RecipeException ex = assertThrows(RecipeException.class, () -> recipe.setAmtSugar("-1"));
        assertEquals("Units of sugar must be a positive integer", ex.getMessage());
    }

    @Test
    @DisplayName("Recipe equals with null should return false")
    void testRecipeEqualsNull() {
        Recipe recipe = new Recipe();
        recipe.setName("Mocha");

        assertFalse(recipe.equals(null));
    }
    
    @Test
    @DisplayName("Recipe equals with different object type should return false")
    void testRecipeEqualsDifferentType() {
        Recipe recipe = new Recipe();
        recipe.setName("Mocha");

        assertFalse(recipe.equals("Mocha"));
    }
    
    @Test
    @DisplayName("Recipe default constructor values")
    void testRecipeDefaultValues() {
        Recipe recipe = new Recipe();

        assertEquals("", recipe.getName());
        assertEquals(0, recipe.getPrice());
        assertEquals(0, recipe.getAmtCoffee());
        assertEquals(0, recipe.getAmtMilk());
        assertEquals(0, recipe.getAmtSugar());
        assertEquals(0, recipe.getAmtChocolate());
    }
    
    @Test
    @DisplayName("Recipe set valid chocolate zero")
    void testRecipeChocolateZero() throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setAmtChocolate("0");

        assertEquals(0, recipe.getAmtChocolate());
    }

    @Test
    @DisplayName("Recipe negative price should throw exception")
    void testRecipeNegativePrice() {
        Recipe recipe = new Recipe();

        RecipeException ex = assertThrows(RecipeException.class, () -> recipe.setPrice("-5"));
        assertEquals("Price must be a positive integer", ex.getMessage());
    }

    @Test
    @Disabled("Intentionally failing test. Fix by changing expected name to Espresso.")
    @DisplayName("Intentional failing recipe book test")
    void intentionallyFailingRecipeBookTest() {
        recipeBook.addRecipe(recipe1);
        assertEquals("Latte", recipeBook.getRecipes()[0].getName());
    }
    @ParameterizedTest
    @CsvSource({
        "Mocha, 10",
        "Latte, 15",
        "Espresso, 8"
    })
    @DisplayName("Parameterized test for adding recipes with different names and prices")
    void testAddRecipeParameterized(String recipeName, String recipePrice) throws RecipeException {
        RecipeBook recipeBook = new RecipeBook();
        Recipe recipe = new Recipe();

        recipe.setName(recipeName);
        recipe.setPrice(recipePrice);

        boolean added = recipeBook.addRecipe(recipe);

        assertAll(
            () -> assertTrue(added),
            () -> assertEquals(recipeName, recipeBook.getRecipes()[0].getName()),
            () -> assertEquals(Integer.parseInt(recipePrice), recipeBook.getRecipes()[0].getPrice())
        );
    }
}