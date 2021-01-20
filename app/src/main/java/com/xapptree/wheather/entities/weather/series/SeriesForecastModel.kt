package com.xapptree.wheather.entities.weather.series

import com.xapptree.wheather.entities.weather.today.TodayForecastModel
import java.io.Serializable

class SeriesForecastModel {
    data class Response(
        var cod: String,
        var message: Int?,
        var cnt: Int?,
        var city: ForecastCity?,
        var list: ArrayList<ForecastListItem>?
    ) : Serializable

    data class ForecastCity(
        val id: Long,
        val name: String,
        val coord: TodayForecastModel.Coordinates?,
        val country: String,
        val population: Long,
        val timezone: Int,
        val sunrise: Long,
        val sunset: Long
    ) : Serializable

    data class ForecastListItem(
        val dt: Long,
        val main: TodayForecastModel.Main?,
        val weather: ArrayList<TodayForecastModel.Weather>?,
        val clouds: TodayForecastModel.Clouds?,
        val wind: TodayForecastModel.Wind?,
        val sys: TodayForecastModel.Sys?,
        val visibility: Long,
        val pop: Int,
        val dt_txt: String
    ) : Serializable
}