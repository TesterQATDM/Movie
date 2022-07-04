package com.ru.tdm.movie.network.movie

import com.ru.tdm.movie.network.base.RetrofitConfig
import com.ru.tdm.movie.app.repository.MovieSource
import com.ru.tdm.movie.network.base.BaseRetrofitSource
import com.ru.tdm.movie.network.model.MovieApiForm
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitMovieSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), MovieSource {

    private val movieApi = retrofit.create(MovieApi::class.java)

    override suspend fun getMovie(): MovieApiForm = wrapRetrofitExceptions {
        delay(1000)
        movieApi.getMovieApi()
    }
}