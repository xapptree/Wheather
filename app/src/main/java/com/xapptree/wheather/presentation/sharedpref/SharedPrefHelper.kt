package com.xapptree.wheather.presentation.sharedpref

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {

    private val SHARED_PREF_NAME = "weather_sp"
    private var sp: SharedPreferences? = null

    init {
        sp = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveBool(key: String, value: Boolean) {
        sp?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBool(key: String): Boolean {
        return sp?.getBoolean(key, false)!!
    }

    fun getBool(key: String, defValue: Boolean): Boolean {
        return sp?.getBoolean(key, defValue)!!
    }

    fun saveString(key: String, value: String) {
        sp?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String): String {
        return sp?.getString(key, "")!!
    }

    fun saveLong(key: String, value: Long) {
        sp?.edit()?.putLong(key, value)?.apply()
    }

    fun getLong(key: String): Long {
        return sp?.getLong(key, 0)!!
    }

    fun remove(key: String) {
        sp?.edit()?.remove(key)?.apply()
    }

    fun getUnits(key: String): String {
        return sp?.getString(key, "standard")!!
    }
}