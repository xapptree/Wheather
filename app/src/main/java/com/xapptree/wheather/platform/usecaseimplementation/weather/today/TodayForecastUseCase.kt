package com.xapptree.wheather.platform.usecaseimplementation.weather.today

import android.util.Log
import com.xapptree.networkrunner.NRResponse
import com.xapptree.networkrunner.NetworkRunner
import com.xapptree.networkrunner.NetworkRunnerCallback
import com.xapptree.networkrunner.RequestType
import com.xapptree.wheather.domain.weather.today.ITodayForecastUseCase
import com.xapptree.wheather.domain.weather.today.ITodayForestProcessable
import com.xapptree.wheather.entities.weather.today.TodayForecastModel
import com.xapptree.wheather.platform.common.Utility

class TodayForecastUseCase(callback: ITodayForestProcessable) : ITodayForecastUseCase,
    NetworkRunnerCallback {
    private var callback: ITodayForestProcessable? = callback
    override fun getTodayForecast(url: String) {
        Log.i("URL", url)
        NetworkRunner.Payload().url(url).type(RequestType.GET)
            .responseType(TodayForecastModel.Response::class.java).callback(this).executeAsync()
    }

    override fun onResponse(response: NRResponse?, requestCode: Int) {
        /*Below Null case wont' happened this will be eliminated in new version of AAR*/
        if (response == null) {
            /*below we can maintain Error model and can validate app level errors*/
            callback?.didReceiveTodayForecastError(response?.message!!)
            return
        }

        if (response.isSuccessful) {
            if (response.body != null) {
                val body = response.body as TodayForecastModel.Response
                callback?.didReceiveTodayForecast(body)
            } else {
                callback?.didReceiveTodayForecastError("No data found")
            }

        } else {
            /*below we can maintain Error model and can validate app level errors*/
            callback?.didReceiveTodayForecastError(response.message!!)
        }
    }

    override fun onFailure(requestCode: Int, throwable: Throwable?) {
        Log.i("ERROR DOMAIN", throwable?.message!!)
        Utility.sendDomainError(callback, throwable)
    }
}