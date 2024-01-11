package com.example.recipebook.controller;

import com.example.recipebook.exception.RecipeNotFoundException;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRecipes() {
        // Arrange
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setTitle("Recipe 1");
        recipe1.setIngredients("Ingredients 1");
        recipe1.setSteps("Steps 1");

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setTitle("Recipe 2");
        recipe2.setIngredients("Ingredients 2");
        recipe2.setSteps("Steps 2");

        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        Mockito.when(recipeService.getAllRecipes()).thenReturn(recipes);

        // Act
        List<Recipe> result = recipeController.getAllRecipes();

        // Assert
        assertEquals(recipes, result);
    }


    @Test
    public void testGetRecipeById() {
        // Arrange
        long recipeId = 1L;
        Recipe recipe = new Recipe();
        Mockito.when(recipeService.getRecipeById(recipeId)).thenReturn(recipe);

        // Act
        ResponseEntity<?> result = recipeController.getRecipeById(recipeId);

        // Assert
        assertEquals(ResponseEntity.ok(recipe), result);
    }

    @Test
    public void testGetRecipeByIdNotFound() {
        // Arrange
        long recipeId = 1L;
        Mockito.when(recipeService.getRecipeById(recipeId)).thenThrow(new RecipeNotFoundException("Recipe not found"));

        // Act
        ResponseEntity<?> result = recipeController.getRecipeById(recipeId);

        // Assert
        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found"), result);
    }

    @Test
    public void testCreateRecipe() {
        // Arrange
        Recipe recipe = new Recipe();
        Mockito.when(recipeService.saveRecipe(recipe)).thenReturn(recipe);

        // Act
        Recipe result = recipeController.createRecipe(recipe);

        // Assert
        assertEquals(recipe, result);
    }

    @Test
    public void testUpdate() {
        // Arrange
        Recipe recipe = new Recipe();
        Mockito.when(recipeService.saveRecipe2(recipe)).thenReturn("Update--Success!!");

        // Act
        String result = recipeController.update(recipe);

        // Assert
        assertEquals("Update--Success!!", result);
    }

    @Test
    public void testUpdateNotFound() {
        // Arrange
        Recipe recipe = new Recipe();
        Mockito.when(recipeService.saveRecipe2(recipe)).thenThrow(new RecipeNotFoundException("Recipe not found"));

        // Act
        String result = recipeController.update(recipe);

        // Assert
        assertEquals("Recipe not found", result);
    }

    @Test
    public void testDeleteRecipe() {
        // Arrange
        long recipeId = 1L;

        // Act
        ResponseEntity<Void> result = recipeController.deleteRecipe(recipeId);

        // Assert
        assertEquals(ResponseEntity.noContent().build(), result);
        Mockito.verify(recipeService).deleteRecipe(recipeId);
    }

    @Test
    public void testPatchPost() {
        // Arrange
        long recipeId = 1L;
        Recipe updatedRecipe = new Recipe();
        Mockito.when(recipeService.patchmethod(recipeId, updatedRecipe)).thenReturn(updatedRecipe);

        // Act
        ResponseEntity<?> result = recipeController.patchPost(recipeId, updatedRecipe);

        // Assert
        assertEquals(new ResponseEntity<>(updatedRecipe, HttpStatus.OK), result);
    }

    @Test
    public void testPatchPostNotFound() {
        // Arrange
        long recipeId = 1L;
        Recipe updatedRecipe = new Recipe();
        Mockito.when(recipeService.patchmethod(recipeId, updatedRecipe)).thenThrow(new RecipeNotFoundException("Recipe not found"));

        // Act
        ResponseEntity<?> result = recipeController.patchPost(recipeId, updatedRecipe);

        // Assert
        assertEquals(new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND), result);
    }
}
