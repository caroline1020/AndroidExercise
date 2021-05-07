package com.caroline.androidexercise.viewmodel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.repository.GitHubRepo
import kotlinx.coroutines.launch
import retrofit2.Response

class UserListViewModel : ViewModel() {


    private var nextPageUrl: String? = FIRST_PAGE_URL
    val result: MutableLiveData<HttpResult<Response<ArrayList<GitHubUser>>>> = MutableLiveData()
    val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int>
        get() = _loading
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
                    updateNextPageUrl(httpResult)

                    result.value = httpResult
                    _loading.value = View.GONE

                }
            }
        }
    }

    private fun updateNextPageUrl(httpResult: HttpResult<Response<ArrayList<GitHubUser>>>) {
        if (httpResult is HttpResult.Success) {
            val headers = httpResult.data.headers()
            val s = headers["link"]
            s?.let {
                val split = it.split(",")
                split.forEach { links ->
                    if (links.contains("next")) {
                        val startIndex = links.indexOf("<") + 1
                        val endIndex = links.indexOf(">")
                        nextPageUrl = links.subSequence(startIndex, endIndex).toString()
                        return
                    }
                }
            }
        }
        return
    }

    fun hasNextPage(): Boolean {
        return !TextUtils.isEmpty(nextPageUrl)
    }

    fun reset() {
        nextPageUrl = FIRST_PAGE_URL
    }

    companion object {
        const val FIRST_PAGE_URL = "https://api.github.com/users?per_page=20"
    }

}
