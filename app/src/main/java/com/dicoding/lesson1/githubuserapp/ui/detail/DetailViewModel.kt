package com.dicoding.lesson1.githubuserapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lesson1.githubuserapp.model.UserResponse
import com.dicoding.lesson1.githubuserapp.model.local.FavoriteRepository
import com.dicoding.lesson1.githubuserapp.model.local.FavoriteUser
import com.dicoding.lesson1.githubuserapp.networking.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    val listUser = MutableLiveData<UserResponse>()
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setDetailUsers(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .getDetailUsers(username)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        listUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("onFailure: ", t.message.toString())
                }
            })
    }

    fun getDetailUsers(): LiveData<UserResponse> {
        return listUser
    }

    fun addToFavorite(userResponse: UserResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            mFavoriteRepository.addToFavorite(
                FavoriteUser(
                    userResponse.id,
                    userResponse.username,
                    userResponse.photo
                )
            )
        }
    }

    fun deleteFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            mFavoriteRepository.deleteFromFavorite(id)
        }
    }

    fun checkUser(id: Int) = mFavoriteRepository.checkUsers(id)
}