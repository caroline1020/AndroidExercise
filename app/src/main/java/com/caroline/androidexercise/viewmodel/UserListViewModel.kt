package com.caroline.androidexercise.viewmodel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.repository.GitHubRepo
import kotlinx.coroutines.launch
import okhttp3.Headers
import retrofit2.Response

class UserListViewModel : ViewModel() {


    private var nextPageUrl: String? = FIRST_PAGE_URL
    val result: MutableLiveData<HttpResult<Response<ArrayList<GitHubUser>>>> = MutableLiveData()
    private val _loading = MutableLiveData<Int>()
    private val repo = GitHubRepo()

    init {
        _loading.value = View.GONE
    }

    fun loadMoreUsers() {
        if (_loading.value == View.GONE) {
            nextPageUrl?.let { url ->
                viewModelScope.launch {
                    _loading.value = View.VISIBLE
                    val httpResult = repo.getUsers(url)
                    if (httpResult is HttpResult.Success) {
                        nextPageUrl = parseNextPageUrl(httpResult.data.headers())
                    }
                    result.value = httpResult
                    _loading.value = View.GONE

                }
            }
        }
    }

    private fun parseNextPageUrl(headers: Headers): String? {
        val s = headers["link"]
        s?.let {
            val split = it.split(",")
            split.forEach { links ->
                if (links.contains("next")) {
                    val startIndex = links.indexOf("<") + 1
                    val endIndex = links.indexOf(">")
                    return links.subSequence(startIndex, endIndex).toString()

                }

            }
        }
        return null

    }

    fun hasNextPage(): Boolean {
        return !TextUtils.isEmpty(nextPageUrl)
    }

    companion object {
        const val FIRST_PAGE_URL = "https://api.github.com/users?per_page=20"
    }

}
