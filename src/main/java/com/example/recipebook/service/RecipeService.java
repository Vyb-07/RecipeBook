package com.example.recipebook.service;

//import org.apache.el.stream.Optional;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recipebook.controller.RecipeController;
import com.example.recipebook.exception.RecipeNotFoundException;
import com.example.recipebook.model.Recipe;
import com.example.recipebook.repository.RecipeRepository;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class RecipeService {
//	@Autowired
    private  RecipeRepository recipeRepository;
    private Logger logger = LoggerFactory.getLogger(RecipeController.class);

    
//    public RecipeService(RecipeRepository recipeRepository) {
//        this.recipeRepository = recipeRepository;
//    }
   // @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
    	logger.info("log--> Fetch is succesful!!");
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
    	logger.info("inside getby ID method");
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));
     
    }


    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
   
    
    public String saveRecipe2(Recipe recipe) {
    	if(recipeRepository.existsById(recipe.getId())) {
    		recipeRepository.save(recipe);
    		return "just updated";
    		}
    	else {
    		logger.warn("Log-->warning Recipe not found!!!");
    	throw new RecipeNotFoundException("Recipe not  found");
    	}
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

	
//	public Recipe patchmethod(long id, Recipe updatedRecipe) {
//        Optional<Recipe> existingPost = RecipeRepository.findById(id);
// 
//        if (existingPost.isPresent()) {
//            Recipe recipeToUpdate = existingPost.get();
// 
//            if (updatedRecipe.getTitle() != null) {
//            	recipeToUpdate.setTitle(updatedRecipe.getTitle());
//            }
// 
//            if (updatedRecipe.getIngredients() != null) {
//            	recipeToUpdate.setIngredients(updatedRecipe.getIngredients());
//            }
//            
//            if (updatedRecipe.getSteps() != null) {
//            	recipeToUpdate.setSteps(updatedRecipe.getSteps());
//            }
//            
// 
//            return RecipeRepository.save(recipeToUpdate);
//        } else {
//            throw new RecipeNotFoundException("Post not found with id: " + id, null);
//        }
//    }
    //--------------------------------------01
//    public Recipe patchmethod(long id, Recipe updatedRecipe) {
//        Optional<Recipe> existingRecipe = recipeRepository.findById(id);
//
//        if (existingRecipe.isPresent()) {
//            Recipe recipeToUpdate = existingRecipe.get();
//
//            if (updatedRecipe.getTitle() != null) {
//                recipeToUpdate.setTitle(updatedRecipe.getTitle());
//            }
//
//            if (updatedRecipe.getIngredients() != null) {
//                recipeToUpdate.setIngredients(updatedRecipe.getIngredients());
//            }
//
//            if (updatedRecipe.getSteps() != null) {
//                recipeToUpdate.setSteps(updatedRecipe.getSteps());
//            }
//
//            return recipeRepository.save(recipeToUpdate);
//        } else {
//        	logger.error("Log-->Error");
//            throw new RecipeNotFoundException("Post not found with id: " + id, null);
//        }
//    }
    //--------------------------------------02

//    public Recipe patchmethod(long id, Recipe updatedRecipe) {
//        Optional<Recipe> existingRecipe = recipeRepository.findById(id);
//
//        if (existingRecipe.isPresent()) {
//            Recipe recipeToUpdate = existingRecipe.get();
//
//            // Update properties without checking for null
//            recipeToUpdate.setTitle(updatedRecipe.getTitle());
//            recipeToUpdate.setIngredients(updatedRecipe.getIngredients());
//            recipeToUpdate.setSteps(updatedRecipe.getSteps());
//
//            return recipeRepository.save(recipeToUpdate);
//        } else {
//            logger.error("Log-->Error");
//            throw new RecipeNotFoundException("Post not found with id: " + id, null);
//        }
//    }
    //--------------------------------------03
    public Recipe patchmethod(long id, Recipe updatedRecipe) {
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);

        if (existingRecipe.isPresent()) {
            Recipe recipeToUpdate = existingRecipe.get();

            // Update only non-null properties
            recipeToUpdate.setTitle(Objects.nonNull(updatedRecipe.getTitle()) ? updatedRecipe.getTitle() : recipeToUpdate.getTitle());
            recipeToUpdate.setIngredients(Objects.nonNull(updatedRecipe.getIngredients()) ? updatedRecipe.getIngredients() : recipeToUpdate.getIngredients());
            recipeToUpdate.setSteps(Objects.nonNull(updatedRecipe.getSteps()) ? updatedRecipe.getSteps() : recipeToUpdate.getSteps());

            return recipeRepository.save(recipeToUpdate);
        } else {
            logger.error("Log-->Error");
            throw new RecipeNotFoundException("Post not found with id: " + id, null);
        }
    }


}
