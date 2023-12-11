package com.aubrey.recepku.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubrey.recepku.data.model.recipe.Favorite
import com.aubrey.recepku.data.model.recommended.Recommended
import com.aubrey.recepku.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import com.aubrey.recepku.data.common.Result

class HomeViewModel(private val recipeRepository: RecipeRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<Result<List<Favorite>>> = MutableStateFlow(Result.Loading)
    val uiState: StateFlow<Result<List<Favorite>>>
        get() = _uiState

    private val _uiState1: MutableStateFlow<Result<List<Recommended>>> = MutableStateFlow(Result.Loading)
    val uiState1: StateFlow<Result<List<Recommended>>>
        get() = _uiState1

    fun getAllRecipes() {
        viewModelScope.launch {
            recipeRepository.getAllRecipes()
                .catch {
                    _uiState.value = Result.Error(it.message.toString())
                }
                .collect { favoriteRecipes ->
                    _uiState.value = Result.Success(favoriteRecipes)
                }
        }
    }

    fun getAllRecommendedRecipes() {
        viewModelScope.launch {
            recipeRepository.getAllRecommendedRecipes()
                .catch {
                    _uiState1.value = Result.Error(it.message.toString())
                }
                .collect { recommendedRecipes ->
                    _uiState1.value = Result.Success(recommendedRecipes)
                }
        }
    }

    fun searchRecipe(query: String) {
        viewModelScope.launch {
            recipeRepository.searchRecipe(query)
                .catch {throwable ->
                    _uiState.value = Result.Error(throwable.message.toString())
                }
                .collect { filteredRecipes ->
                    _uiState.value = Result.Success(filteredRecipes.map { Favorite(it) })
                }
        }
    }
}