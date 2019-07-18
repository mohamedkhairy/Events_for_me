package khairy.com.eventsforme.webAccess

import khairy.com.eventsforme.model.WeatherData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface WeatherEndPoints {
    @GET("forecast?id=360630&appid=b6907d289e10d714a6e88b30761fae22")
    fun getWeatherApi(): Deferred<WeatherData>

}