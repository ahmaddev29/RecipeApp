package com.example.myrecipeapp.domain.repository

import com.example.myrecipeapp.data.remote.dto.RecipeDTO

interface RecipeRepository {
    suspend fun getAllRecipes(): List<RecipeDTO>
    suspend fun getRecipeByID(id: Int): RecipeDTO
}