package com.ru.tdm.movie.app.repository

import com.ru.tdm.movie.network.model.MovieApiForm
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitMovieRepository @Inject constructor(
    private val movieSource: MovieSource
){

    suspend fun getRetrofitMovieRepository(): MovieApiForm {
        return movieSource.getMovie()
    }
}