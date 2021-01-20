package com.xapptree.wheather.presentation.modules.weather

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xapptree.wheather.BuildConfig
import com.xapptree.wheather.domain.weather.today.ITodayForestProcessable
import com.xapptree.wheather.entities.weather.today.TodayForecastModel
import com.xapptree.wheather.platform.repositories.WeatherRepository
import com.xapptree.wheather.platform.room.City
import com.xapptree.wheather.platform.usecaseimplementation.weather.today.TodayForecastUseCaseProvider
import com.xapptree.wheather.presentation.sharedpref.SharedPrefConstants
import com.xapptree.wheather.presentation.sharedpref.SharedPrefHelper

class MainViewModel() : ViewModel() , ITodayForestProcessable{
    private var cities: LiveData<List<City>>? = null
    private var todayForecastAPISuccess :MutableLiveData<TodayForecastModel.Response> = MutableLiveData()
    private var todayForecastAPIError :MutableLiveData<String> = MutableLiveData()
    private var domainError :MutableLiveData<Int> = MutableLiveData()

    fun addCities(context: Context, city: List<City>) {
        WeatherRepository.insertCities(context, city)
    }

    fun addCity(context: Context, city: City) {
        WeatherRepository.insertCity(context, city)
    }

    fun removeCity(context: Context, city: City) {
        WeatherRepository.removeCity(context, city)
    }

    fun removeAllCities(context: Context) {
        WeatherRepository.removeAllCities(context)
    }

    fun getCities(context: Context): LiveData<List<City>>? {
        cities = WeatherRepository.getAllCities(context)
        return cities
    }

    fun getTodayForecast(context: Context,city: City){
        val units = SharedPrefHelper(context).getUnits(SharedPrefConstants.UNITS)
        val useCaseProvider = TodayForecastUseCaseProvider()
        val useCase = useCaseProvider.provideTodayForecastUseCase(this)
        useCase.getTodayForecast("http://api.openweathermap.org/data/2.5/weather?q=${city.city_name}&units=${units}&appid=${BuildConfig.APP_ID}")

    }

    fun onTodayForecastAPISuccess(): MutableLiveData<TodayForecastModel.Response>? {
        return todayForecastAPISuccess
    }

    fun onTodayForecastAPIError(): MutableLiveData<String>? {
        return todayForecastAPIError

    }
    fun onDomainError(): MutableLiveData<Int>? {
        return domainError

    }
    override fun didReceiveTodayForecast(response: TodayForecastModel.Response) {
        todayForecastAPISuccess.postValue(response)
    }

    override fun didReceiveTodayForecastError(error: String) {
        todayForecastAPIError.postValue(error)
    }

    override fun onDomainReachableError(errorCode: Int) {
        domainError.postValue(errorCode)
    }
}