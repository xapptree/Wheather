package com.xapptree.wheather.domain.weather.today

import com.xapptree.wheather.domain.common.IDomainReachable
import com.xapptree.wheather.entities.weather.today.TodayForecastModel

interface ITodayForestProcessable : IDomainReachable {
    fun didReceiveTodayForecast(response: TodayForecastModel.Response)
    fun didReceiveTodayForecastError(error: String)
}