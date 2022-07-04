package com.ru.tdm.movie.network.model

data class MovieApiForm(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)