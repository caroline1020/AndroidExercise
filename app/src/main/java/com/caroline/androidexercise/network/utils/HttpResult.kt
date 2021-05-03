package com.caroline.androidexercise.network.utils

sealed class HttpResult<out T: Any> {
    class Success<out T: Any>(val data: T): HttpResult<T>()
    class httpError(val exception: Throwable): HttpResult<Nothing>()
    class Error(val exception: String): HttpResult<Nothing>()
}