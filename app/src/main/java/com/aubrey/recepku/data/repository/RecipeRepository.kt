package com.aubrey.recepku.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.aubrey.recepku.data.model.recipe.Favorite
import com.aubrey.recepku.data.model.recipe.RecipeData
import com.aubrey.recepku.data.model.recommended.Recommended
import com.aubrey.recepku.data.model.recommended.RecommendedRecipeData
import com.aubrey.recepku.data.response.RecipeResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.data.database.FavDao
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.data.response.ErrorResponse
import com.aubrey.recepku.data.retrofit.ApiService
import java.io.IOException

class RecipeRepository private constructor(
    private val apiService: ApiService,
    private val favDao: FavDao
) {

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

    fun getAllRecipes(): LiveData<Result<RecipeResponse>> = liveData {
        emit(Result.Loading)
        try {
            val recipeResponse = apiService.getRecipe()
            if (recipeResponse.message == "success") {
                emit(Result.Success(recipeResponse))
            } else {
                // Handle unsuccessful response
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            emit(Result.Error("Failed: $errorMessage"))
        } catch (e: Exception) {
            emit(Result.Error("Internet Issues"))
        } catch (e: SocketTimeoutException) {
            emit(Result.Error("Read timeout occurred"))
        } catch (e: IOException) {
            emit(Result.Error("Network error occurred"))
        }
    }

    fun searchRecipe(query: String): LiveData<Result<List<DataItem?>?>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getRecipeByTitle(query)
                val recipes = response.data?.filter { it?.title?.contains(query, ignoreCase = true) == true ||
                        query.contains(it?.title.orEmpty(), ignoreCase = true) }
                emit(Result.Success(recipes))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getAllRecommendedRecipes(): Flow<List<Recommended>> {
        return flow {
            emit(recommendedRecipes)
        }
    }

    fun getAllFavoriteRecipes(): LiveData<List<FavoriteRecipe>> = favDao.getFav()

    fun getFavoriteRecipeById(id: Int): LiveData<FavoriteRecipe?> = favDao.getFavoriteById(id)

    suspend fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe) {
        favDao.insert(favoriteRecipe)
    }

    suspend fun deleteFavoriteRecipe(favoriteRecipe: FavoriteRecipe) {
        favDao.delete(favoriteRecipe)
    }

    suspend fun search(recipe : String) : RecipeResponse {
        return apiService.getRecipeByTitle(recipe)
    }

    companion object {
        @Volatile
        private var instance: RecipeRepository? = null

        fun getInstance(apiService: ApiService, favDao: FavDao): RecipeRepository =
            instance ?: synchronized(this) {
                instance ?: RecipeRepository(apiService, favDao).also { instance = it }
            }
    }
}