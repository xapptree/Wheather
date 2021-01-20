package com.xapptree.wheather.platform.usecaseimplementation.weather.today

import com.xapptree.wheather.domain.weather.today.ITodayForecastUseCase
import com.xapptree.wheather.domain.weather.today.ITodayForecastUseCaseProvider
import com.xapptree.wheather.domain.weather.today.ITodayForestProcessable

class TodayForecastUseCaseProvider : ITodayForecastUseCaseProvider {
    override fun provideTodayForecastUseCase(callback: ITodayForestProcessable): ITodayForecastUseCase {
        return TodayForecastUseCase(callback)
    }
}