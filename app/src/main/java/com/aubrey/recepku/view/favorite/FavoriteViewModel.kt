package com.aubrey.recepku.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.repository.RecipeRepository
import kotlinx.coroutines.launch
import com.aubrey.recepku.data.common.Result
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel (private val repository: RecipeRepository) : ViewModel(){
    fun getAllFavoriteRecipe(): LiveData<Result<List<FavoriteRecipe>>> {
        return repository.getAllFavoriteRecipes()
    }


    fun insert(favorite: FavoriteRecipe) = viewModelScope.launch {
        repository.insertFavoriteRecipe(favorite)
    }

    fun delete(favorite: FavoriteRecipe) = viewModelScope.launch {
        repository.deleteFavoriteRecipe(favorite)
    }

    fun cleanUpDatabase() = viewModelScope.launch {
        repository.cleanUpFavorites()
    }


}