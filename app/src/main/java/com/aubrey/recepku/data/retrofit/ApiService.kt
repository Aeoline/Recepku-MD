package com.aubrey.recepku.data.retrofit

import com.aubrey.recepku.data.response.LoginResponse
import com.aubrey.recepku.data.response.ProfileResponse
import com.aubrey.recepku.data.response.RecipeResponse
import com.aubrey.recepku.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("/makanan/all")
    suspend fun getRecipe(
        @Query("search") search: String? = null,
    ): RecipeResponse

    @GET("profile")
    suspend fun getProfile(
    ): ProfileResponse
}