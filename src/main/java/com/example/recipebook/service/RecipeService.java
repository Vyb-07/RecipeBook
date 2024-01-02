package com.example.recipebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recipebook.exception.RecipeNotFoundException;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.repository.RecipeRepository;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));
    }


    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
   
    
    public String saveRecipe2(Recipe recipe) {
    	if(recipeRepository.existsById(recipe.getId())) {
    		recipeRepository.save(recipe);
    		return "ipde update chesa";
    		}
    	else {
    	throw new RecipeNotFoundException("Recipe not mg found");
    	}
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
