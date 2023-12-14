package com.aubrey.recepku.view.favorite

import androidx.lifecycle.ViewModel
import com.aubrey.recepku.data.repository.RecipeRepository

class FavoriteViewModel (private val repository: RecipeRepository) : ViewModel(){
    fun getAllFavoriteRecipe() = repository.getAllFavoriteRecipes()

}