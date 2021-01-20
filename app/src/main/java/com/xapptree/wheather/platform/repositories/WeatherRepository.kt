package com.xapptree.wheather.platform.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.xapptree.wheather.platform.room.City
import com.xapptree.wheather.platform.room.WeatherDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class WeatherRepository() {
    companion object {
        private var database: WeatherDatabase? = null

        private var cities: LiveData<List<City>>? = null

        private fun initializeDB(context: Context): WeatherDatabase {
            return WeatherDatabase.getDatabase(context)
        }

        fun insertCities(context: Context, city: List<City>) {
            database = initializeDB(context)

            CoroutineScope(IO).launch {
                database?.cityDao()?.insertCities(city)
            }

        }

        fun insertCity(context: Context, city: City) {
            database = initializeDB(context)

            CoroutineScope(IO).launch {
                database?.cityDao()?.insert(city)
            }

        }
        fun removeCity(context: Context, city: City) {
            database = initializeDB(context)

            CoroutineScope(IO).launch {
                database?.cityDao()?.delete(city)
            }

        }

        fun removeAllCities(context: Context) {
            database = initializeDB(context)

            CoroutineScope(IO).launch {
                database?.cityDao()?.deleteAll()
            }

        }

        fun getAllCities(context: Context): LiveData<List<City>>? {
            database = initializeDB(context)
            cities = database?.cityDao()?.getAllCities()
            return cities
        }
    }
}
