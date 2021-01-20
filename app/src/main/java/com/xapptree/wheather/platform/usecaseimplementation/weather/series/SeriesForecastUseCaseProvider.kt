package com.xapptree.wheather.platform.usecaseimplementation.weather.series

import com.xapptree.wheather.domain.weather.series.ISeriesForecastProcessable
import com.xapptree.wheather.domain.weather.series.ISeriesForecastUseCase
import com.xapptree.wheather.domain.weather.series.ISeriesForecastUseCaseProvider

class SeriesForecastUseCaseProvider : ISeriesForecastUseCaseProvider {
    override fun provideSeriesForecastUseCase(callback: ISeriesForecastProcessable): ISeriesForecastUseCase {
        return SeriesForecastUseCase(callback)
    }
}