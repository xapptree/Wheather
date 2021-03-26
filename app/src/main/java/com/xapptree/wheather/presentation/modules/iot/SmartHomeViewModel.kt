package com.xapptree.wheather.presentation.modules.iot

import android.content.Context
import android.content.SharedPreferences
import android.net.wifi.ScanResult
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

class SmartHomeViewModel() : ViewModel(){
    private var ssidList :MutableLiveData<List<ScanResult>> = MutableLiveData()

    fun getSSIDs(): LiveData<List<ScanResult>>? {
        return ssidList
    }

    fun addSSIDs(data:List<ScanResult>){
        ssidList.postValue(data)
    }
}