package com.xapptree.wheather.entities.weather.today

import java.io.Serializable

class TodayForecastModel {
    data class Response(
        var coord: Coordinates?,
        var weather: ArrayList<Weather>?,
        var base: String,
        var main: Main?,
        var visibility: String?,
        var wind: Wind?,
        var clouds: Clouds?,
        var dt: Long,
        var sys: Sys?,
        var timezone: Long?,
        var id: Long?,
        var name: String?,
        var cod: Int,
        var message:String?
    ) : Serializable

    data class Coordinates(val lon: Double?, val lat: Double?) : Serializable
    data class Weather(val id: Int?, val main: String?, val description: String?, val icon: String?) :
        Serializable

    data class Main(
        val temp: Double?,
        val feels_like: Double?,
        val temp_min: Double?,
        val temp_max: Double?,
        val pressure: Int?,
        val humidity: Int?,
        val sea_level: Int?,
        val gmd_level: Int?,
        val temp_kf: Double?
    ) : Serializable

    data class Wind(val speed: Double?, val deg: Int?) : Serializable
    data class Clouds(val all: Int?) : Serializable
    data class Sys(
        val type: Int?,
        val id: Int?,
        val country: String?,
        val sunrise: Long?,
        val sunset: Long?,
        val pod:String?
    ) : Serializable
}