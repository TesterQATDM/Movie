package com.ru.tdm.movie.app.screen.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ru.tdm.movie.app.repository.RetrofitMovieRepository
import com.ru.tdm.movie.app.screen.base.BaseViewModel
import com.ru.tdm.movie.app.utils.logger.Logger
import com.ru.tdm.movie.app.utils.requireValue
import com.ru.tdm.movie.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    movieRepository: RetrofitMovieRepository,
    logger: Logger
): BaseViewModel(movieRepository, logger)  {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    fun loadCity() = viewModelScope.safeLaunch {
        showProgress()
        try {
            delay(3000)
        } finally {
            hideProgress()
        }
    }

    private fun showProgress() {
        _state.value = State(progress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(progress = false)
    }

    data class State(
        val progress: Boolean = false
    ) {
        val showProgress: Boolean get() = progress
    }
}