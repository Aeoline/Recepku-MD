package com.aubrey.recepku.data.repository

import com.aubrey.recepku.data.model.recipe.Favorite
import com.aubrey.recepku.data.model.recipe.Recipe
import com.aubrey.recepku.data.model.recipe.RecipeData
import com.aubrey.recepku.data.model.recommended.Recommended
import com.aubrey.recepku.data.model.recommended.RecommendedRecipeData
import com.aubrey.recepku.data.publicretrofit.PublicApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepository private constructor(private val publicApiService: PublicApiService) {

    private val favoriteRecipes = mutableListOf<Favorite>()
    private val recommendedRecipes = mutableListOf<Recommended>()

    init {
        if (favoriteRecipes.isEmpty()) {
            RecipeData.recipes.forEach {
                favoriteRecipes.add(Favorite(it))
            }
        }

        if (recommendedRecipes.isEmpty()) {
            RecommendedRecipeData.recommended_recipes.forEach {
                recommendedRecipes.add(Recommended(it))
            }
        }
    }

    fun getAllRecipes(): Flow<List<Favorite>> {
        return flow {
            emit(favoriteRecipes)
        }
    }

    fun getAllRecommendedRecipes(): Flow<List<Recommended>> {
        return flow {
            emit(recommendedRecipes)
        }
    }


    fun searchRecipe(query: String): Flow<List<Recipe>> {
        return flow {
            val filteredList = favoriteRecipes.filter { it.recipe.title.contains(query, ignoreCase = true) }
                .map { it.recipe }
            emit(filteredList)
        }
    }

    companion object {
        @Volatile
        private var instance: RecipeRepository? = null

        fun getInstance(publicApiService: PublicApiService): RecipeRepository =
            instance ?: synchronized(this) {
                instance ?: RecipeRepository(publicApiService).also { instance = it }
            }
    }
}