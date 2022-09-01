package com.dicoding.lesson1.githubuserapp.model

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("items")
    val items: ArrayList<UserResponse>,

    @SerializedName("total_count")
    val totalCount: Int
)
