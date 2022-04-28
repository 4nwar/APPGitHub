package com.example.sub2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavUserDao {
    @Insert
    fun addtoFav(favoriteUser : FavoriteUser)

    @Query("SELECT * FROM FavUser")
    fun getFavUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM FavUser WHERE FavUser.id = :id")
    suspend fun cekUser(id:Int): Int

    @Query("DELETE from FavUser WHERE FavUser.id = :id")
    suspend fun delFromFavo(id: Int) :Int
}