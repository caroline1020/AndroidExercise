package com.caroline.androidexercise.network.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpUtil {
    fun init(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        var builder = OkHttpClient.Builder().addInterceptor(logging)

        return builder.build()
    }
}