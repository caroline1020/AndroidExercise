package com.caroline.androidexercise.repository

import com.caroline.androidexercise.network.GitHubApi
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.utils.HttpResult
import retrofit2.Response

/**
 * Created by nini on 2021/5/3.
 */
class GitHubRepo {
    suspend fun getUsers(): HttpResult<Response<ArrayList<GitHubUser>>> {

        return try {
            val result = GitHubApi.service.getUsers()
            HttpResult.Success(result)
        } catch (e: Throwable) {
            HttpResult.Error(e)
        }

    }

}