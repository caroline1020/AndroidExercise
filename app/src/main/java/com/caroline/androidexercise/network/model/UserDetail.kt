package com.caroline.androidexercise.network.model

import com.google.gson.annotations.SerializedName

class UserDetail(
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val username: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean,
    @SerializedName("location")
    val location: String,
    @SerializedName("blog")
    val blog: String,
    @SerializedName("bio")
    val bio: String
) {

    companion object {

        fun emptyObject(): UserDetail {
            return UserDetail("", "", "", false, "", "", "")
        }

    }
}
