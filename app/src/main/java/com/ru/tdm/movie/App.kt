package com.ru.tdm.movie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Entry point of the app should be annotated with [HiltAndroidApp].
 */

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}