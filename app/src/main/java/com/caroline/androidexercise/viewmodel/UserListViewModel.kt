package com.caroline.androidexercise.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.repository.GitHubRepo
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {

    val result: MutableLiveData<HttpResult<ArrayList<GitHubUser>>> = MutableLiveData()
    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int>
        get() = _loading
    private val repo = GitHubRepo()

    init {
        _loading.value = View.GONE
        getUserList()
    }


    private fun getUserList() {
        if (_loading.value == View.GONE) {
            viewModelScope.launch {
                _loading.value = View.VISIBLE
                result.value = repo.getUsers()
                _loading.value = View.GONE

            }
        }
    }


}
