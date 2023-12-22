package com.marzuki.bigerapp.data

sealed class ResultState<out T> {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val message: String?, val exception: Throwable? = null) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}
