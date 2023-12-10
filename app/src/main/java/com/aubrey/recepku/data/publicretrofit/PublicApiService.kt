package com.aubrey.recepku.data.publicretrofit

import com.aubrey.recepku.data.response.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicApiService {
    @GET("json/v1/1/lookup.php?i=52777")

    suspend fun getPublicRecipe(): RecipeListResponse
}