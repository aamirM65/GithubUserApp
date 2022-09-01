package com.dicoding.lesson1.githubuserapp.model.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "table_favorite")
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val username: String?,

    @field:SerializedName("avatar_url")
    val photo: String?,
) : Parcelable