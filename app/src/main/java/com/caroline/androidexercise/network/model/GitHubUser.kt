package com.caroline.androidexercise.network.model

import com.google.gson.annotations.SerializedName

class GitHubUser(
    @SerializedName("login")
    val userId: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
) {


}
