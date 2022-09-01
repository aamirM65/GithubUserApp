package com.dicoding.lesson1.githubuserapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lesson1.githubuserapp.model.SearchUserResponse
import com.dicoding.lesson1.githubuserapp.model.UserResponse
import com.dicoding.lesson1.githubuserapp.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private var listUsers = MutableLiveData<ArrayList<UserResponse>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearchUsers(query: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .getSearchUsers(query)
            .enqueue(object : Callback<SearchUserResponse> {
                override fun onResponse(
                    call: Call<SearchUserResponse>,
                    responseSearch: Response<SearchUserResponse>
                ) {
                    _isLoading.value = false
                    if (responseSearch.isSuccessful){
                        listUsers.postValue(responseSearch.body()?.items)
                    }
                }

                override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.d("onFailure: ", t.message.toString())
                }

            })
    }
    fun getSearchUsers(): LiveData<ArrayList<UserResponse>>{
        return listUsers
    }
}