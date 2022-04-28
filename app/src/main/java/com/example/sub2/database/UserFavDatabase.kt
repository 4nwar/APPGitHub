package com.example.sub2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext
import java.time.Instant

@Database(
    entities = [FavoriteUser::class],
    version = 1
)
abstract class UserFavDatabase: RoomDatabase(){
    companion object{
        var INSTANCE : UserFavDatabase? = null

        fun getDatabase(context: Context): UserFavDatabase?{
            if (INSTANCE==null){
                synchronized(UserFavDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserFavDatabase::class.java, "UserFav_database")
                        .build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun favitemUserDao(): FavUserDao
}