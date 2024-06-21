package com.aubrey.recepku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
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
import com.aubrey.recepku.data.response.RecommendedResponse
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
            Log.d("API Response", "Response: $recipeResponse")

            if (recipeResponse.message == "success") {
                emit(Result.Success(recipeResponse))
            } else {
                Log.d("API Error", "Unsuccessful response: ${recipeResponse.message}")
                emit(Result.Error("Failed to fetch recipes: ${recipeResponse.message}"))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            Log.e("HttpException", "Error: $errorMessage", e)
            emit(Result.Error("Failed: $errorMessage"))
        } catch (e: Exception) {
            Log.e("Exception", "Error: ${e.message}", e)
            emit(Result.Error("Internet Issues: ${e.message}"))
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeoutException", "Timeout occurred", e)
            emit(Result.Error("Read timeout occurred"))
        } catch (e: IOException) {
            Log.e("IOException", "Network error occurred", e)
            emit(Result.Error("Network error occurred"))
        }
    }

    fun getFavoriteRecipes(): LiveData<Result<RecommendedResponse>> = liveData {
        emit(Result.Loading)
        try {
            val recipeResponse = apiService.getFavRecipe()
            Log.d("API Response Recommended Recipe", "Response: $recipeResponse")
            if (recipeResponse.message == "success") {
                emit(Result.Success(recipeResponse))
            } else {
                Log.d("API Error Recipe Recommended", "Unsuccessful response: ${recipeResponse.message}")
                emit(Result.Error("Failed to fetch favorite recipes: ${recipeResponse.message}"))
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody?.message ?: "An error occurred"
            Log.e("HttpException", "Error: $errorMessage", e)
            emit(Result.Error("Failed: $errorMessage"))
        } catch (e: Exception) {
            Log.e("Exception", "Error: ${e.message}", e)
            emit(Result.Error("Internet Issues: ${e.message}"))
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeoutException", "Timeout occurred", e)
            emit(Result.Error("Read timeout occurred"))
        } catch (e: IOException) {
            Log.e("IOException", "Network error occurred", e)
            emit(Result.Error("Network error occurred"))
        }
    }




    fun searchRecipe(query: String): LiveData<Result<List<DataItem?>?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getRecipeByTitle(query)
            val recipes = response.data?.filter {
                val slug = it?.slug.orEmpty()
                Log.d("SearchDebug", "Slug: $slug")
                val keywords = query.split(" ").filter { it.isNotEmpty() }
                Log.d("SearchDebug", "Keywords: $keywords")
                // Check if any keyword in the query is contained in the slug
                val containsKeyword = keywords.any { keyword ->
                    slug.contains(keyword, ignoreCase = true)
                }
                Log.d("SearchDebug", "Contains keyword: $containsKeyword")
                containsKeyword
            }
            Log.d("SearchDebug", "Filtered recipes: ${recipes?.size}")
            emit(Result.Success(recipes))
        } catch (e: Exception) {
            Log.e("SearchDebug", "Error: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
    }



    fun getAllRecommendedRecipes(): Flow<List<Recommended>> {
        return flow {
            emit(recommendedRecipes)
        }
    }

    fun getAllFavoriteRecipes(): LiveData<Result<List<FavoriteRecipe>>> = liveData {
        emit(Result.Loading)
        try {
            val source = favDao.getFav()
            emitSource(source.map { favoriteRecipes ->
                if (favoriteRecipes.isNullOrEmpty()) {
                    Result.Error("No favorite recipes found")
                } else {
                    Result.Success(favoriteRecipes)
                }
            })
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }



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

    fun isFavorite(recipeId: Int?): LiveData<Boolean> {
        return favDao.isFavorite(recipeId)
    }

    suspend fun cleanUpFavorites() {
        favDao.deleteNullEntries()
        favDao.deleteDuplicateEntries()
    }

    suspend fun isRecipeFavorite(recipeId: Int): Boolean {
        return favDao.isFavorite(recipeId) != null
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