package com.dicoding.lesson1.githubuserapp.ui.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lesson1.githubuserapp.model.UserResponse
import com.dicoding.lesson1.githubuserapp.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    val listUsers = MutableLiveData<ArrayList<UserResponse>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setListFollowers(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .getFollowersUsers(username)
            .enqueue(object : Callback<ArrayList<UserResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<UserResponse>>,
                    response: Response<ArrayList<UserResponse>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("onFailure: ", t.message.toString())
                }
            })
    }

    fun getListFollowers(): LiveData<ArrayList<UserResponse>> {
        return listUsers
    }
}