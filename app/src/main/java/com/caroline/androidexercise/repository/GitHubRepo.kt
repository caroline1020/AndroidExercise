package com.caroline.androidexercise.repository

import com.caroline.androidexercise.network.GitHubApi
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.model.UserDetail
import com.caroline.androidexercise.network.utils.HttpResult
import retrofit2.Response

/**
 * Created by nini on 2021/5/3.
 */
class GitHubRepo {

    suspend fun getUsers(url: String): HttpResult<Response<ArrayList<GitHubUser>>> {

        return try {
            val response = GitHubApi.service.getUsers(url)
            if (response.isSuccessful) {
                HttpResult.Success(response)
            } else {
                HttpResult.ApiError(response.errorBody().toString())
            }
        } catch (e: Throwable) {
            HttpResult.HttpError(e)
        }

    }

    suspend fun getUserDetail(username: String): HttpResult<Response<UserDetail>> {

        return try {
            val response = GitHubApi.service.getUserDetail(username)
            if (response.isSuccessful) {
                val result = response.body() ?: UserDetail.emptyObject()
                HttpResult.Success(response)
            } else {
                HttpResult.ApiError(response.errorBody().toString())
            }
        } catch (e: Throwable) {
            HttpResult.HttpError(e)
        }

    }


}