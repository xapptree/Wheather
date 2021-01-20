package com.xapptree.wheather.domain.weather.today

interface ITodayForecastUseCaseProvider {
    fun provideTodayForecastUseCase(callback: ITodayForestProcessable): ITodayForecastUseCase
}