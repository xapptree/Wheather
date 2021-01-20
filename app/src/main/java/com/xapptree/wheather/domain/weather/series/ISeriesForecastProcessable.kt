package com.xapptree.wheather.domain.weather.series

import com.xapptree.wheather.domain.common.IDomainReachable
import com.xapptree.wheather.entities.weather.series.SeriesForecastModel

interface ISeriesForecastProcessable : IDomainReachable {
    fun didReceiveSeriesForecast(response: SeriesForecastModel.Response)
    fun didReceiveSeriesForecastError(error: String)
}