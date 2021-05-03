package com.caroline.androidexercise.network.model

import android.view.View
import com.google.gson.annotations.SerializedName

class GitHubUser(
    @SerializedName("login")
    val userId: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("site_admin")
    val staff: Boolean
) {

    fun badgeVisibility(): Int {
        return if (staff)
            View.VISIBLE
        else
            View.GONE
    }
}
