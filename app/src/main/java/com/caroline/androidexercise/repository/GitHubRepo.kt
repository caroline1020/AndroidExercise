package com.caroline.androidexercise.repository

import com.caroline.androidexercise.network.GitHubApi
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.utils.HttpResult

/**
 * Created by nini on 2021/5/3.
 */
class GitHubRepo {
    suspend fun getUsers(): HttpResult<ArrayList<GitHubUser>> {

        return try {
            val response = GitHubApi.service.getUsers()
            if(response.isSuccessful) {
                val result = response.body() ?: ArrayList()
                HttpResult.Success(result)
            }else{
                HttpResult.Error(response.errorBody().toString())
            }
        } catch (e: Throwable) {
            HttpResult.httpError(e)
        }

    }

}