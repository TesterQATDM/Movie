package com.ru.tdm.movie.network.movie

import com.ru.tdm.movie.network.model.MovieApiForm
import retrofit2.http.GET

interface MovieApi {

    @GET("trending/all/day?api_key=6f7875dc080eccc792837e287ee28c93")
    suspend fun getMovieApi(): MovieApiForm
}