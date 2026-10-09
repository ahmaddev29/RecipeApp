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


class HomeViewModel: ViewModel() {

    private val repository: RecipeRepository = RecipeRepositoryImpl(apiService = RecipeAPIService(
        KtorClient.client))

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var recipes by mutableStateOf<List<RecipeDTO>>(emptyList())
        private set

    var categories by mutableStateOf<List<String>>(listOf("All"))
        private set

    var selectedCategory by mutableStateOf("All")
        private set

    private var allRecipes: List<RecipeDTO> = emptyList()

    init {
        fetchRecipes()
    }

    fun fetchRecipes(){
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                val result = repository.getAllRecipes()
                allRecipes = result

                val cuisines = result.map { it.cuisine }.distinct().sorted()
                categories = listOf("All") + cuisines

                applyFilters()
            } catch (e: Exception) {
                errorMessage = e.message ?: "An unexpected error occurred"
            } finally {
                isLoading = false
            }
        }
    }

    fun onCategorySelected(category: String){
        selectedCategory = category
        applyFilters()
    }

    private fun applyFilters(){
        recipes = if (selectedCategory == "All") allRecipes else allRecipes.filter { it.cuisine == selectedCategory }
    }
}