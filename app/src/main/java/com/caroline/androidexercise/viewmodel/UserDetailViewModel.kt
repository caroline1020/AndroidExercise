package com.caroline.androidexercise.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caroline.androidexercise.network.model.UserDetail
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.repository.GitHubRepo
import kotlinx.coroutines.launch

class UserDetailViewModel : ViewModel() {

    val result: MutableLiveData<HttpResult<UserDetail>> = MutableLiveData()
    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int>
        get() = _loading
    private val repo = GitHubRepo()

    init {
        _loading.value = View.GONE
    }


    fun getUserDetail(username: String) {
        if (_loading.value == View.GONE) {
            viewModelScope.launch {
                _loading.value = View.VISIBLE
                result.value = repo.getUserDetail(username)
                _loading.value = View.GONE
            }
        }
    }


}
