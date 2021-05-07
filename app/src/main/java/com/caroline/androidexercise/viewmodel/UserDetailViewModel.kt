package com.caroline.androidexercise.viewmodel

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.*
import com.caroline.androidexercise.network.model.UserDetail
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.repository.GitHubRepo
import kotlinx.coroutines.launch
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private lateinit var username: String
    val result: MutableLiveData<HttpResult<Response<UserDetail>>> = MutableLiveData()
    val userDetail: MutableLiveData<UserDetail> = MutableLiveData()
    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int>
        get() = _loading
    private val repo = GitHubRepo()

    init {
        _loading.value = View.GONE
    }

    val badgeVisibility: LiveData<Int> = Transformations.map(userDetail) {
        if (it.siteAdmin) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun getUserDetail() {
        if (TextUtils.isEmpty(username)) {
            return
        }
        if (_loading.value == View.GONE) {
            viewModelScope.launch {
                _loading.value = View.VISIBLE
                val httpResult = repo.getUserDetail(username)
                result.value = httpResult
                if (httpResult is HttpResult.Success) {
                    userDetail.value = httpResult.data.body()
                }
                _loading.value = View.GONE
            }
        }
    }

    fun setUsername(username: String) {
        this.username = username
    }


}
