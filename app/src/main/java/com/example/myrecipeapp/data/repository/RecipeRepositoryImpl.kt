package com.example.myrecipeapp.data.repository

import com.example.myrecipeapp.data.remote.RecipeAPIService
import com.example.myrecipeapp.data.remote.dto.RecipeDTO
import com.example.myrecipeapp.domain.repository.RecipeRepository

class RecipeRepositoryImpl(private val apiService: RecipeAPIService): RecipeRepository {

    override suspend fun getAllRecipes(): List<RecipeDTO> {
        return apiService.getAllRecipes().recipes
    }

    override suspend fun getRecipeByID(id: Int): RecipeDTO {
        return apiService.getRecipeByID(id)
    }
}