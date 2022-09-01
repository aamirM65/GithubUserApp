package com.dicoding.lesson1.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lesson1.githubuserapp.model.local.FavoriteRepository
import com.dicoding.lesson1.githubuserapp.model.local.FavoriteUser

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
        return mFavoriteRepository.getFavoriteUsers()
    }

}