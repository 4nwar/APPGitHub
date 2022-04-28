package com.example.sub2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.sub2.database.FavUserDao
import com.example.sub2.database.FavoriteUser
import com.example.sub2.database.UserFavDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application){

    private var userDao: FavUserDao?
    private var userDb: UserFavDatabase?

    init{
        userDb = UserFavDatabase.getDatabase(application)
        userDao = userDb?.favitemUserDao()
    }

    fun getFavUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavUser()
    }
}