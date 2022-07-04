package com.ru.tdm.movie.di

import com.ru.tdm.movie.app.repository.MovieSource
import com.ru.tdm.movie.app.utils.logger.LogCatLogger
import com.ru.tdm.movie.app.utils.logger.Logger
import com.ru.tdm.movie.network.movie.RetrofitMovieSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module binds concrete sources implementations to their
 * interfaces: [RetrofitMovieSource] is bound to [MovieSource].
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {

    @Binds
    abstract fun bindMovieSource(
        retrofitMovieSource: RetrofitMovieSource
    ): MovieSource
}