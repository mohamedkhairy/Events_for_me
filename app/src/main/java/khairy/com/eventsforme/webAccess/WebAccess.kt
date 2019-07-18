package khairy.com.eventsforme.webAccess

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object WebAccess {

    private val BASE_URL = "https://openweathermap.org/data/2.5/"

    val weatherApi : WeatherEndPoints by lazy {

        Log.d("WebAccess", "Creating retrofit client")
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Moshi maps JSON to classes
            .addConverterFactory(MoshiConverterFactory.create())
            // The call adapter handles threads
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        // Create Retrofit client
        return@lazy retrofit.create(WeatherEndPoints::class.java)
    }
}