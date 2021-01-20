package com.xapptree.wheather.domain.weather.series

interface ISeriesForecastUseCaseProvider {
    fun provideSeriesForecastUseCase(callback:ISeriesForecastProcessable):ISeriesForecastUseCase
}