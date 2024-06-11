package com.aubrey.recepku.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.model.recipe.Recipe
import com.aubrey.recepku.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    fun insert(favorite: FavoriteRecipe) = viewModelScope.launch {
        repository.insertFavoriteRecipe(favorite)
    }

    fun delete(favorite: FavoriteRecipe) = viewModelScope.launch {
        repository.deleteFavoriteRecipe(favorite)
    }
}