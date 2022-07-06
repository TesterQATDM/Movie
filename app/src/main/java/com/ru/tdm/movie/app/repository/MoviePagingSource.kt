package com.ru.tdm.movie.app.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ru.tdm.movie.network.model.Result
import com.ru.tdm.movie.network.movie.MovieApi
import retrofit2.HttpException

class MoviePagingSource constructor(
    private val movieApi: MovieApi,
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: 1
        try {
            val response = movieApi.getMovieApiPage(page)
            val results = response.results
            val nextPageNumber = if (response.page == null) null else page + 1
            val prevPageNumber = if (page > 1) page - 1 else null
            Log.e("MyLog", nextPageNumber.toString())
            return LoadResult.Page(results, prevPageNumber, nextPageNumber)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}