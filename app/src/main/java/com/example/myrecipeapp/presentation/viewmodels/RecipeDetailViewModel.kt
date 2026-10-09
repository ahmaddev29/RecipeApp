package com.example.myrecipeapp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.remote.KtorClient
import com.example.myrecipeapp.data.remote.RecipeAPIService
import com.example.myrecipeapp.data.remote.dto.RecipeDTO
import com.example.myrecipeapp.data.repository.RecipeRepositoryImpl
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDetailViewModel: ViewModel() {

    private val repository: RecipeRepository = RecipeRepositoryImpl(
        apiService = RecipeAPIService(
            KtorClient.client
        )
    )

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var recipe by mutableStateOf<RecipeDTO?>(null)
        private set

    fun fetchRecipeDetail(id: Int){
        isLoading = true
        errorMessage = null
        try {
            viewModelScope.launch {
                recipe = repository.getRecipeByID(id)
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "An unexpected error occurred"
        } finally {
            isLoading = false
        }
    }

}