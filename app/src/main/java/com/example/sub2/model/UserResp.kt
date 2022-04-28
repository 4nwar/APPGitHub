package com.example.sub2.model

import com.google.gson.annotations.SerializedName

data class UserResp(
    @SerializedName("items")
    val items : ArrayList<User>
)
