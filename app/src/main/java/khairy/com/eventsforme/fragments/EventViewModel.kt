package khairy.com.eventsforme.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import arrow.core.left
import khairy.com.eventsforme.model.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import khairy.com.eventsforme.extensions.*
import khairy.com.eventsforme.webAccess.WebAccess

class EventViewModel(app: Application) : AndroidViewModel(app){

    private val jobs: MutableList<Job> = mutableListOf()

    private val liveLoading: MutableLiveData<Boolean> = MutableLiveData()

    private val liveResult: MutableLiveData<Either<Result.Error, WeatherData?>> = MutableLiveData()

    fun getLiveWeatherData(): LiveData<Either<Result.Error, WeatherData?>> {
        return liveResult
    }

    fun getLoading(): LiveData<Boolean> = liveLoading

    private fun postLoading(isLoading: Boolean): Unit = liveLoading.postValue(isLoading)


    fun getWeatherData() {
        jobs += GlobalScope.launch(Dispatchers.IO) {
            postLoading(true)
            val r: Either<Result.Error, Result.Success<WeatherData?>> =
                callAPI { WebAccess.weatherApi.getWeatherApi() }
            ListResult(r)
        }
    }

    private fun ListResult(result: Either<Result.Error, Result.Success<WeatherData?>>) {
        when (result) {
            is Either.Right -> {
                if (result.b.t.isNotNull()) rightSide(result.b)
                else leftSide(Result.Error(Errors.UnknownError))
            }
            is Either.Left  -> leftSide(result.a)
        }
    }

    private fun rightSide(r: Result.Success<WeatherData?>) {
        postLoading(false)
        liveResult.postValue(Either.right(r.t))
    }

    private fun leftSide(l: Result.Error) {
        postLoading(false)
        liveResult.postValue(l.left())
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { if (it.isActive) it.cancel() }
    }
}