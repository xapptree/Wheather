package com.xapptree.wheather.platform.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    @ColumnInfo(name = "city_name") var city_name: String,
    @ColumnInfo(name = "latitude") var latitude: String,
    @ColumnInfo(name = "longitude") var longitude: String,
){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

}