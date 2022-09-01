package com.dicoding.lesson1.githubuserapp.networking

import com.dicoding.lesson1.githubuserapp.model.SearchUserResponse
import com.dicoding.lesson1.githubuserapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getAllUsers(): Call<ArrayList<UserResponse>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") username: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowersUsers(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUsers(
        @Path("username") username: String
    ): Call<ArrayList<UserResponse>>
}