package com.xapptree.wheather.domain.weather.today

interface ITodayForecastUseCase {
    fun getTodayForecast(url:String)
}