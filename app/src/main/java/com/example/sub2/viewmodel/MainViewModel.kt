package com.example.sub2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sub2.conf.ApiConf
import com.example.sub2.model.User
import com.example.sub2.model.UserResp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    val users = MutableLiveData<ArrayList<User>>()

    fun setSearchUser(query: String){
        ApiConf.ApiService
            .getSearchUser(query)
            .enqueue(object : Callback<UserResp>{
                override fun onResponse(call: Call<UserResp>, response: Response<UserResp>) {
                    if (response.isSuccessful){
                        users.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResp>, t: Throwable) {
                    t.message?.let { Log.d("gagal", it)
                    }
                }
            })
    }

    fun getSearchUser() :LiveData<ArrayList<User>>{
        return users
    }
}