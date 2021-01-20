package com.xapptree.wheather.platform.common

import com.xapptree.networkrunner.NetworkRunnerConfig


object Initializer {
    fun initRetrofit(url: String) {
        NetworkRunnerConfig.Builder()
            .setBaseUrl(url)
            .setHttps(false)
            .setTimeOut(60000)
            .build()
    }

}