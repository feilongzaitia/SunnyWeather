package com.example.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var contenxt:Context
        const val TOKEN:String = "R6yIU6YPlyk8RHna"

    }

    override fun onCreate() {
        super.onCreate()
        contenxt = applicationContext
    }
}