package khairy.com.eventsforme.calenderUtil

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class CalenderReader  {


    lateinit var  cur : Cursor

    private val EVENT_SELECTION: Array<String> = arrayOf(
        CalendarContract.Events.CALENDAR_ID, //0
        CalendarContract.Events.TITLE,  // 1
        CalendarContract.Events.DTSTART,// 2
        CalendarContract.Events.EVENT_LOCATION   // 3)
    )


     fun showResult(ac : Activity ) {
         try {
             val uri: Uri = CalendarContract.Events.CONTENT_URI
              cur = ac.contentResolver.query(uri, EVENT_SELECTION, null, null, null)

         }catch (e:Exception){
             Log.d("xxxx0" , "ERRORR")
            e.printStackTrace()
         }
    }

     fun getEventName(index: Int):String{
            cur.moveToFirst()
            cur.move(index)
            val displayName: String = cur.getString(EVENT_NAME_INDEX)
        return displayName
    }

    fun getStartDate(index: Int):String{
        cur.moveToFirst()
        cur.move(index)
        val date = getDate(java.lang.Long.parseLong(cur.getString(EVENT_DATE_INDEX)))

        return date
    }

    fun getlocation(index: Int):String{
        cur.moveToFirst()
        cur.move(index)
        val description = cur.getString(EVENT_DESCR_INDEX)

        if (description.isNullOrEmpty()) return "No Location"

        return description
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(milliSeconds: Long): String {
        val formatter = SimpleDateFormat(
            "dd/MM/yyyy hh:mm:ss a"
        )
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    companion object {
        private const val PROJECTION_ID_INDEX: Int = 0
        private const val EVENT_NAME_INDEX: Int = 1
        private const val EVENT_DATE_INDEX: Int = 2
        private const val EVENT_DESCR_INDEX: Int = 3

    }
}