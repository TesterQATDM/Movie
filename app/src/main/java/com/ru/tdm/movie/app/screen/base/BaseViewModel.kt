package com.ru.tdm.movie.app.screen.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.tdm.movie.app.utils.logger.Logger
import com.ru.tdm.movie.R
import com.ru.tdm.movie.app.repository.RetrofitMovieRepository
import com.ru.tdm.movie.app.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel(
    val movieRepository: RetrofitMovieRepository,
    private val logger: Logger
) : ViewModel() {

    private val _showErrorMessageResEvent = MutableLiveEvent<Int>()
    val showErrorMessageResEvent = _showErrorMessageResEvent.share()

    private val _showErrorMessageEvent = MutableLiveEvent<String>()
    val showErrorMessageEvent = _showErrorMessageEvent.share()

    fun CoroutineScope.safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: ConnectionException) {
                logError(e)
                _showErrorMessageResEvent.publishEvent(R.string.connection_error)
            } catch (e: BackendException) {
                logError(e)
                //_showErrorMessageEvent.publishEvent("${e.message}")
                _showErrorMessageEvent.publishEvent("Данные не найдены")
            } catch (e: Exception) {
                logError(e)
                _showErrorMessageResEvent.publishEvent(R.string.internal_error)
            }
        }
    }

    private fun logError(e: Throwable) {
        logger.error(javaClass.simpleName, e)
        tryAgain()
    }

    open fun tryAgain(){
        Log.e("log", "BaseViewModel")
    }
}