package com.example.recipebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recipebook.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
