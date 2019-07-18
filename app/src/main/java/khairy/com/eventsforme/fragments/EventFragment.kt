package khairy.com.eventsforme.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import arrow.core.Either
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import khairy.com.eventsforme.R
import khairy.com.eventsforme.adapter.ViewAdapter
import khairy.com.eventsforme.calenderUtil.CalenderReader
import khairy.com.eventsforme.extensions.Result
import khairy.com.eventsforme.extensions.isNotNull
import khairy.com.eventsforme.model.WeatherData
import kotlinx.android.synthetic.main.event_fragment.*
import java.util.concurrent.TimeUnit

class EventFragment : Fragment() {

    private lateinit var adapter: ViewAdapter
    private var isloading = false
    private val event = CalenderReader()



    private val eventViewModel: EventViewModel by lazy {
        ViewModelProviders.of(this).get(EventViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.event_fragment , container, false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoading()
        eventViewModel.getWeatherData()
        prepareView()
        getCalendarEvents()
        observeApiResult()
        refreshingData()

    }

    @SuppressLint("CheckResult")
    private fun refreshingData(){
        val timer = Observable.interval(30L, TimeUnit.SECONDS)
            .timeInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                eventViewModel.getWeatherData()
                Log.d("xxxx", "Run")
            }

        stop_btn.setOnClickListener { timer.dispose() }
    }

    private fun prepareView() {

        val recyclerManeger = LinearLayoutManager(activity)
        recyclerView.layoutManager = recyclerManeger
        recyclerView.hasFixedSize()

    }

    private fun getCalendarEvents(){
        activity?.let {
             event.showResult(it)
        }
    }

    private fun observeApiResult() {
        eventViewModel.getLiveWeatherData()
            .observe(this, Observer { rb: Either<Result.Error, WeatherData?> ->

                rb.fold(
                    { Log.d("xxx", "Error")
                        adapter = ViewAdapter(null , event)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    },
                    {
                        isloading = false

                        if (rb.isNotNull()){
                            adapter = ViewAdapter(it , event)
                            recyclerView.adapter = adapter
                            adapter.notifyDataSetChanged()
                        }
                    })


            })
    }

    private fun observeLoading() {
        eventViewModel.getLoading().observe(this, Observer { optionLoading: Boolean? ->
            optionLoading?.let { loading ->
                when (loading) {
                    true -> {
                        subloading.visibility = View.VISIBLE
                    }
                    else -> {
                        subloading.visibility = View.GONE
                    }
                }
            }
        })
    }


    companion object {
        const val TAG = "EVENTFRAGMENT"
    }
    }