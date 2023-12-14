package com.aubrey.recepku.view.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class FavoriteViewModel (private val repository: RecipeRepository) : ViewModel(){
    fun getAllFavoriteRecipe() = repository.getAllFavoriteRecipes()

    fun getFavoriteRecipes(id: Int) = repository.getFavoriteRecipeById(id)

    fun insert(favorite: FavoriteRecipe) = viewModelScope.launch {
        repository.insertFavoriteRecipe(favorite)
    }

    fun delete(favorite: FavoriteRecipe) = viewModelScope.launch {
        repository.deleteFavoriteRecipe(favorite)
    }


}