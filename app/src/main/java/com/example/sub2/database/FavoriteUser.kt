package com.example.sub2.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "FavUser")
data class FavoriteUser(
    val login: String?,
    @PrimaryKey
    val id: Int,
    val avatar_url: String?,
    val name: String?
):Serializable
