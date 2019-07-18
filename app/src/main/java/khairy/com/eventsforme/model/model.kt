package khairy.com.eventsforme.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize



@JsonClass(generateAdapter = true)
@Parcelize
data class WeatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherDate>,
    val message: Double
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val timezone: Int
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Coord(
    val lat: Double,
    val lon: Double
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class WeatherDate(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Wind(
    val deg: Double,
    val speed: Double
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Clouds(
    val all: Int
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Main(
    val grnd_level: Double,
    val humidity: Int,
    val pressure: Double,
    val sea_level: Double,
    val temp: Double,
    val temp_kf: Float,
    val temp_max: Double,
    val temp_min: Double
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Sys(
    val pod: String
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
):Parcelable