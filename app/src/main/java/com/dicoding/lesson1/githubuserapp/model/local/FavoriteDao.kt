package com.dicoding.lesson1.githubuserapp.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM table_favorite")
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("DELETE FROM table_favorite WHERE id = :id")
    fun deleteFromFavorite(id: Int): Int

    @Query("SELECT COUNT(*) FROM table_favorite WHERE id = :id")
    fun checkUser(id: Int): Int
}