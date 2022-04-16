package com.guko.assignment6.retrofit

import androidx.viewbinding.BuildConfig
import com.guko.assignment6.util.Common
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class NetworkHelper {
    var userService : UserService? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Common.BASE_URL)
            .client((getClient()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        userService = retrofit.create(UserService::class.java)
    }

    private fun getClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(60,TimeUnit.SECONDS)
        httpClient.writeTimeout(60,TimeUnit.SECONDS)
        httpClient.connectTimeout(60,TimeUnit.SECONDS)
        httpClient.addInterceptor(createHttpInterceptor(BuildConfig.DEBUG))
        return httpClient.build()
    }

    private fun createHttpInterceptor(debugMode: Boolean): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if(debugMode)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }
}

