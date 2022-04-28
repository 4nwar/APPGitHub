package com.example.sub2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sub2.conf.ApiConf
import com.example.sub2.database.FavUserDao
import com.example.sub2.database.FavoriteUser
import com.example.sub2.database.UserFavDatabase
import com.example.sub2.model.UserDetailResp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<UserDetailResp>()

    private var userDao: FavUserDao?
    private var userDb: UserFavDatabase?

    init{
        userDb = UserFavDatabase.getDatabase(application)
        userDao = userDb?.favitemUserDao()
    }

    fun setUserDetail(username : String) {
        ApiConf.ApiService
            .getUserdetail(username)
            .enqueue(object : Callback<UserDetailResp> {
                override fun onResponse(call: Call<UserDetailResp>,response: Response<UserDetailResp>) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResp>, t: Throwable) {
                    t.message?.let {
                        Log.d("gagal", it)
                    }
                }

            })
    }
    fun getUserDetail(): LiveData<UserDetailResp>{
        return user
    }

    fun addToFav(username: String?, id:Int, avatar_url: String?, name: String?){
        CoroutineScope(Dispatchers.IO).launch{
            var user = FavoriteUser(
                username,
                id,
                avatar_url,
                name
            )
            userDao?.addtoFav(user)
        }
    }

    suspend fun cekUser(id:Int) = userDao?.cekUser(id)

    fun delFromFavo(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.delFromFavo(id)
        }
    }
}