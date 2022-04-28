package com.example.sub2.model

data class UserDetailResp(
    val login: String,
    val name: String,
    val id: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int,
    val location : String,
    val company: String?
)
