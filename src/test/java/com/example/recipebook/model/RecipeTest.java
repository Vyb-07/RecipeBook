package com.example.recipebook.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Recipe recipe = new Recipe();
        Long id = 1L;
        String title = "Test Recipe";
        String ingredients = "Ingredient 1, Ingredient 2";
        String steps = "Step 1, Step 2";

        // Act
        recipe.setId(id);
        recipe.setTitle(title);
        recipe.setIngredients(ingredients);
        recipe.setSteps(steps);

        // Assert
        assertEquals(id, recipe.getId(), "ID should match");
        assertEquals(title, recipe.getTitle(), "Title should match");
        assertEquals(ingredients, recipe.getIngredients(), "Ingredients should match");
        assertEquals(steps, recipe.getSteps(), "Steps should match");
    }
}
