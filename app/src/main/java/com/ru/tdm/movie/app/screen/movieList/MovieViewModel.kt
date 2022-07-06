package com.ru.tdm.movie.app.screen.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.ru.tdm.movie.app.repository.MoviePagingSource
import com.ru.tdm.movie.network.model.Result
import com.ru.tdm.movie.network.movie.MovieApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieApi: MovieApi
): ViewModel() {

    fun getMovies(): Flow<PagingData<Result>> =
        Pager(config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { MoviePagingSource(movieApi) }
        ).flow.cachedIn(viewModelScope)


}