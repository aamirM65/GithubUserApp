package com.dicoding.lesson1.githubuserapp.model.local

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteDao.getFavoriteUsers()

    fun addToFavorite(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteDao.addToFavorite(favoriteUser) }
    }

    fun deleteFromFavorite(id: Int) {
        executorService.execute { mFavoriteDao.deleteFromFavorite(id) }
    }

    fun checkUsers(id: Int) = mFavoriteDao.checkUser(id)
}