package com.aubrey.recepku.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aubrey.recepku.data.model.recommended.Recommended
import com.aubrey.recepku.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.repository.UserRepository
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.data.response.RecipeResponse
import com.aubrey.recepku.data.userpref.ProfileModel
import com.aubrey.recepku.data.userpref.UserPreferences
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val recipeRepository: RecipeRepository, private val repository: UserRepository,private val userPreferences: UserPreferences) : ViewModel() {


    private val recipeList = MutableLiveData<Result<RecipeResponse>>()
    val recipeData: LiveData<Result<RecipeResponse>> = recipeList

    private val _search = MutableLiveData<String>()
    val search: LiveData<String> = _search

    private val _uiState1: MutableStateFlow<Result<List<Recommended>>> = MutableStateFlow(Result.Loading)
    val uiState1: StateFlow<Result<List<Recommended>>>
        get() = _uiState1


    fun getRecipes() {
        viewModelScope.launch {
            val recipeResponse = recipeRepository.getAllRecipes()
            recipeResponse.asFlow().collect {
                recipeList.value = it
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

    fun getFavoriteRecipes(id: Int) = recipeRepository.getFavoriteRecipeById(id)

    fun insert(favorite: FavoriteRecipe) = viewModelScope.launch {
        recipeRepository.insertFavoriteRecipe(favorite)
    }

    fun delete(favorite: FavoriteRecipe) = viewModelScope.launch {
        recipeRepository.deleteFavoriteRecipe(favorite)
    }

    companion object {
        var username = "bakso"
    }

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val results = recipeRepository.search(query)
                _search.value = results.toString()
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    fun deleteUser() = repository.deleteUser()

    fun saveUser(): LiveData<ProfileModel>{
        return userPreferences.getCookie().asLiveData()
    }
}