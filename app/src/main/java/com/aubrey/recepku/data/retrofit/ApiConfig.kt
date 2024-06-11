package com.aubrey.recepku.data.retrofit

import com.aubrey.recepku.tools.HealthyStepsDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private var URL = "https://backend-recepku-oop-rnrqe2wc3a-et.a.run.app/"
        var token = ""
        var cookie = ""

        fun getApiService(token: String): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val authInterceptor = Interceptor{chain ->
                val req = chain.request()
                val requestHeader = req.newBuilder()
                    .addHeader("Authorization","Bearer $token ")
                    .build()
                chain.proceed(requestHeader)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()

            val gson = GsonBuilder()
                .registerTypeAdapter(object : TypeToken<List<String>>() {}.type, HealthyStepsDeserializer())
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}