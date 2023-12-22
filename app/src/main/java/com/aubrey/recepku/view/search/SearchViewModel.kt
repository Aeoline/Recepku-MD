package com.aubrey.recepku.view.search

import androidx.lifecycle.ViewModel
import com.aubrey.recepku.data.repository.RecipeRepository

class SearchViewModel (private val repository: RecipeRepository) : ViewModel() {
    fun searchRecipe(search: String) = repository.searchRecipe(search)
}