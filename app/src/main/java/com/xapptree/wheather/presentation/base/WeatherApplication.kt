package com.xapptree.wheather.presentation.base

import android.app.Application
import com.xapptree.wheather.BuildConfig
import com.xapptree.wheather.platform.common.Initializer.initRetrofit

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initRetrofit(BuildConfig.BASE_URL)
    }
}