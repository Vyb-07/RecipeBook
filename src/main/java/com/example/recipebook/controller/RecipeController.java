package com.example.recipebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recipebook.exception.RecipeNotFoundException;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.service.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
//        return recipeService.getRecipeById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(recipeService.getRecipeById(id));
//                    .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with ID: " + id)));
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }

    @PutMapping
    public String update(@RequestBody Recipe recipe) {
    	try {
        recipeService.saveRecipe2(recipe);
         return "Update--Success!!";
    	}
    	catch(RecipeNotFoundException e) {
    		return e.getMessage();
    	}
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
//        return recipeService.getRecipeById(id)
//                .map(existingRecipe -> {
//                    existingRecipe.setTitle(updatedRecipe.getTitle());
//                    existingRecipe.setIngredients(updatedRecipe.getIngredients());
//                    existingRecipe.setSteps(updatedRecipe.getSteps());
//                    return ResponseEntity.ok(recipeService.saveRecipe(existingRecipe));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
