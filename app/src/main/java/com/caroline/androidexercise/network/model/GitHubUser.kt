package com.caroline.androidexercise.network.model

import android.view.View
import com.google.gson.annotations.SerializedName

class GitHubUser(
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("site_admin")
    val staff: Boolean,
    @SerializedName("id")
    val id: Int
) {

    fun badgeVisibility(): Int {
        return if (staff)
            View.VISIBLE
        else
            View.GONE
    }
}
