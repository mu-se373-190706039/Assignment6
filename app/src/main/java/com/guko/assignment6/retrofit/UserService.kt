package com.guko.assignment6.retrofit

import com.guko.assignment6.model.User
import com.guko.assignment6.model.UserAuth
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    @FormUrlEncoded
    @POST("/login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Call<UserAuth>

    @FormUrlEncoded
    @POST("/register.php")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("gender") gender: String
    ) : Call<UserAuth>
}