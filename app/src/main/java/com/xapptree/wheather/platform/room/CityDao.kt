package com.xapptree.wheather.platform.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(city: List<City>)

    @Delete
    fun delete(city: City)

    @Query("DELETE FROM city")
    fun deleteAll()

    @Update
    fun update(city: City)

    @Query("SELECT * FROM city")
    fun getAllCities(): LiveData<List<City>>
}