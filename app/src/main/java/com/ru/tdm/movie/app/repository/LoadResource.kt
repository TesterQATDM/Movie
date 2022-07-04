package com.ru.tdm.movie.app.repository

sealed class LoadResource <T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
    ){

    class Success<T>(data: T): LoadResource<T>(Status.SUCCESS, data)
    class Loading<T>(data: T? = null): LoadResource<T>(Status.LOADING, data)
    class Error<T>(message: String?, data: T? = null): LoadResource<T>(Status.ERROR, data, message)
}

enum class Status{
    SUCCESS,
    LOADING,
    ERROR
}