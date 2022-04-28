package com.example.sub2.conf

import com.example.sub2.model.User
import com.example.sub2.model.UserDetailResp
import com.example.sub2.model.UserResp
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET ("search/users")
    @Headers ("Authorization: token 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")
    fun getSearchUser(
        @Query ("q") query: String
    ):Call<UserResp>

    @GET ("users/{username}")
    @Headers ("Authorization: token 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")
    fun getUserdetail(
        @Path ("username")  username : String
    ):Call<UserDetailResp>

    @GET ("users/{username}/followers")
    @Headers ("Authorization: token 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")
    fun getFollowers(
        @Path ("username")  username : String
    ):Call<ArrayList<User>>

    @GET ("users/{username}/following")
    @Headers ("Authorization: token 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")
    fun getFollowing(
        @Path ("username")  username : String
    ):Call<ArrayList<User>>
}