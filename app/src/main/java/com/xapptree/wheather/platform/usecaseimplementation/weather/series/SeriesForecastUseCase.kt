package com.xapptree.wheather.platform.usecaseimplementation.weather.series

import com.xapptree.networkrunner.NRResponse
import com.xapptree.networkrunner.NetworkRunner
import com.xapptree.networkrunner.NetworkRunnerCallback
import com.xapptree.networkrunner.RequestType
import com.xapptree.wheather.domain.weather.series.ISeriesForecastProcessable
import com.xapptree.wheather.domain.weather.series.ISeriesForecastUseCase
import com.xapptree.wheather.entities.weather.series.SeriesForecastModel
import com.xapptree.wheather.platform.common.Utility

class SeriesForecastUseCase(callback: ISeriesForecastProcessable) : ISeriesForecastUseCase,
    NetworkRunnerCallback {
    private var callback: ISeriesForecastProcessable? = callback
    override fun getSeriesForecast(url: String) {
        NetworkRunner.Payload().url(url).type(RequestType.GET)
            .responseType(SeriesForecastModel.Response::class.java).callback(this).executeAsync()
    }

    override fun onResponse(response: NRResponse?, requestCode: Int) {
        /*Below Null case wont' happened this will be eliminated in new version of AAR*/
        if (response == null) {
            /*below we can maintain Error model and can validate app level errors*/
            callback?.didReceiveSeriesForecastError(response?.message!!)
            return
        }

        if (response.isSuccessful) {
            if (response.body != null) {
                val body = response.body as SeriesForecastModel.Response
                callback?.didReceiveSeriesForecast(body)
            } else {
                callback?.didReceiveSeriesForecastError("Data not found")
            }

        } else {
            /*below we can maintain Error model and can validate app level errors*/
            callback?.didReceiveSeriesForecastError(response.message!!)
        }
    }

    override fun onFailure(requestCode: Int, throwable: Throwable?) {
        Utility.sendDomainError(callback, throwable)
    }
}