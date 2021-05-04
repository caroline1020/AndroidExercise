package com.caroline.androidexercise.repository

import com.caroline.androidexercise.network.GitHubApi
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.model.UserDetail
import com.caroline.androidexercise.network.utils.HttpResult

/**
 * Created by nini on 2021/5/3.
 */
class GitHubRepo {
    suspend fun getUsers(since: Int, pageSize: Int): HttpResult<ArrayList<GitHubUser>> {

        return try {
            val response = GitHubApi.service.getUsers(since, pageSize.toString())
            if (response.isSuccessful) {
                val result = response.body() ?: ArrayList()
                HttpResult.Success(result)
            } else {
                HttpResult.apiError(response.errorBody().toString())
            }
        } catch (e: Throwable) {
            HttpResult.httpError(e)
        }

    }

    suspend fun getUserDetail(username: String): HttpResult<UserDetail> {

        return try {
            val response = GitHubApi.service.getUserDetail(username)
            if (response.isSuccessful) {
                val result = response.body() ?: UserDetail.emptyObject()
                HttpResult.Success(result)
            } else {
                HttpResult.apiError(response.errorBody().toString())
            }
        } catch (e: Throwable) {
            HttpResult.httpError(e)
        }

    }
}