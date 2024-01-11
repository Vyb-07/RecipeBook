package com.example.recipebook.service;

import com.example.recipebook.exception.RecipeNotFoundException;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRecipes() {
        // Arrange
        List<Recipe> recipes = new ArrayList<>();
        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);

        // Act
        List<Recipe> result = recipeService.getAllRecipes();

        // Assert
        assertEquals(recipes, result);
    }

    @Test
    public void testGetRecipeById() {
        // Arrange
        long recipeId = 1L;
        Recipe recipe = new Recipe();
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // Act
        Recipe result = recipeService.getRecipeById(recipeId);

        // Assert
        assertEquals(recipe, result);
    }

    @Test
    public void testGetRecipeByIdNotFound() {
        // Arrange
        long recipeId = 1L;
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RecipeNotFoundException.class, () -> recipeService.getRecipeById(recipeId));
    }

    @Test
    public void testSaveRecipe() {
        // Arrange
        Recipe recipe = new Recipe();
        Long id = 1L;
        recipe.setId(id);

        // Mock the behavior of recipeRepository.save
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        // Act
        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        // Assert
        assertNotNull(savedRecipe, "Saved recipe should not be null");
        assertEquals(id, savedRecipe.getId());
        // Add more assertions based on your specific requirements
    }


    @Test
    public void testSaveRecipe2Exists() {
        // Arrange
        Recipe recipe = new Recipe();
        when(recipeRepository.existsById(recipe.getId())).thenReturn(true);

        // Act and Assert
        assertDoesNotThrow(() -> recipeService.saveRecipe2(recipe));
        verify(recipeRepository).save(recipe);
    }

    @Test
    public void testSaveRecipe2NotExists() {
        // Arrange
        Recipe recipe = new Recipe();
        when(recipeRepository.existsById(recipe.getId())).thenReturn(false);

        // Act and Assert
        RecipeNotFoundException getexception = assertThrows(RecipeNotFoundException.class, 
        		() -> { recipeService.saveRecipe2(recipe); });
        assertEquals("Recipe not  found", getexception.getMessage()); // Updated message here
    }


    @Test
    public void testDeleteRecipe() {
        // Arrange
        long recipeId = 1L;

        // Act
        assertDoesNotThrow(() -> recipeService.deleteRecipe(recipeId));

        // Assert
        verify(recipeRepository).deleteById(recipeId);
    }

    @Test
    public void testPatchMethod() {
        // Arrange
        long recipeId = 1L;
        Recipe existingRecipe = new Recipe();
        Recipe updatedRecipe = new Recipe();
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(existingRecipe));
        Mockito.when(recipeRepository.save(existingRecipe)).thenReturn(existingRecipe);

        // Act
        Recipe result = recipeService.patchmethod(recipeId, updatedRecipe);

        // Assert
        assertEquals(existingRecipe, result);
        Mockito.verify(recipeRepository).findById(recipeId);
        Mockito.verify(recipeRepository).save(existingRecipe);
    }

    @Test
    public void testPatchMethodNotFound() {
        // Arrange
        long recipeId = 1L;
        Recipe updatedRecipe = new Recipe();
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RecipeNotFoundException.class, () -> recipeService.patchmethod(recipeId, updatedRecipe));
        Mockito.verify(recipeRepository, Mockito.never()).save(Mockito.any());
    }
}

