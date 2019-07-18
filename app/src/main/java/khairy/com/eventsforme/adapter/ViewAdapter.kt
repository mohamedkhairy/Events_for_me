package khairy.com.eventsforme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import khairy.com.eventsforme.R
import khairy.com.eventsforme.calenderUtil.CalenderReader
import khairy.com.eventsforme.extensions.isNull
import khairy.com.eventsforme.model.WeatherData
import kotlinx.android.synthetic.main.view_event_item.view.*

class ViewAdapter(var weatherData: WeatherData?, var event: CalenderReader ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_event_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val calendarOrder = event.cur.count -1 -position
        (holder as CardViewHolder).bind(weatherData?.city?.name,
            weatherData?.list?.get(position)?.main?.temp.toString(),
            weatherData?.list?.get(position)?.weather?.get(0)?.description,
            weatherData?.list?.get(position)?.main?.humidity.toString(),
            weatherData?.list?.get(position)?.weather?.get(0)?.icon,
            event.getEventName(calendarOrder),
            event.getStartDate(calendarOrder),
            event.getlocation(calendarOrder))
    }

    override fun getItemCount(): Int {
        if(weatherData.isNull())return event.cur.count
        else return weatherData!!.cnt
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherCountry: String? ,temp:String? ,description: String?,humidity: String? , icon: String?,
                 eventname: String , date: String , location: String ) {


            loadImage(icon)
            itemView.event_title.text = eventname
            itemView.event_date.text = date
            itemView.event_description.text = location
            itemView.cuntry_name.text = weatherCountry
            itemView.temp.text = temp
            itemView.description.text = description
            itemView.humidity.text = humidity

            itemView.setOnClickListener {
                itemView.expandable_layout.toggle()
            }
        }

        private fun loadImage(icon: String?){

            val url = "http://openweathermap.org/img/w/$icon.png"
            Glide.with(itemView)
                .load(url)
                .into(itemView.weather_icon)

        }
    }
}