package com.caroline.androidexercise.network

import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.model.UserDetail
import com.caroline.androidexercise.network.utils.OkHttpUtil
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by nini on 2021/05/03.
 */

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://api.github.com/")
    .client(OkHttpUtil.init())
    .build()

interface APIService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") per_page: String
    ): Response<ArrayList<GitHubUser>>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): Response<UserDetail>
}

object GitHubApi {
    val service: APIService by lazy {
        retrofit.create(APIService::class.java)
    }

}