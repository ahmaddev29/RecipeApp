package com.example.myrecipeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myrecipeapp.presentation.screens.home.HomeScreen
import com.example.myrecipeapp.presentation.screens.recipe_detail.RecipeDetailScreen

@Composable
fun RecipeNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ){

        composable<HomeRoute> {
            HomeScreen(
                onRecipeClick = { id ->
                    navController.navigate(RecipeDetailRoute(id))
                }
            )
        }

        composable <RecipeDetailRoute> { backStackEntry ->

            val detailRoute = backStackEntry.toRoute<RecipeDetailRoute>()

            RecipeDetailScreen(
                recipeId = detailRoute.recipeId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}