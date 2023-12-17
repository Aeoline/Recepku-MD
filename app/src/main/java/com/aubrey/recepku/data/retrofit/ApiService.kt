package com.aubrey.recepku.data.retrofit

import androidx.room.Delete
import com.aubrey.recepku.data.response.CheckUserResponse
import com.aubrey.recepku.data.response.DeleteResponse
import com.aubrey.recepku.data.response.EditPassResponse
import com.aubrey.recepku.data.response.EditUserResponse
import com.aubrey.recepku.data.response.LoginResponse
import com.aubrey.recepku.data.response.RecipeResponse
import com.aubrey.recepku.data.response.RegisterResponse
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("/makanan/")
    suspend fun getRecipeByTitle(
        @Query("title") title: String?
    ): RecipeResponse

    @FormUrlEncoded
    @PUT("/profile/username")
    suspend fun editUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): EditUserResponse

    @FormUrlEncoded
    @PUT("/profile/password")
    suspend fun editPass(
        @Field("newPassword") newPassword: String,
        @Field("confirmPassword") confirmPassword: String,
        @Field("Password") password: String
    ): EditPassResponse

    @GET("user")
    suspend fun checkUser(): CheckUserResponse

    @DELETE("profile")
    suspend fun deleteUser(): DeleteResponse
}