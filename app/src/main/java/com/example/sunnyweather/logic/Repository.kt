package com.example.sunnyweather.logic


import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {
    fun searchPlaces(query:String) =liveData(Dispatchers.IO){
        val result = try {
            val placeRespone = SunnyWeatherNetwork.searchPlaces(query)
            if (placeRespone.status == "ok"){
                val places = placeRespone.places
                Result.success(places)
            }
            else{
                Result.failure(RuntimeException("respone status is ${placeRespone.status}"))
            }
        }
        catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }

}