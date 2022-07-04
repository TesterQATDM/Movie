package com.ru.tdm.movie.app.repository

import com.ru.tdm.movie.network.model.MovieApiForm

interface MovieSource{

    suspend fun getMovie(): MovieApiForm

}