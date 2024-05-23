package com.example.fitness_app.utils.Objects

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
object Time {

    val formater = SimpleDateFormat("mm:ss")
    val wokOutformater = SimpleDateFormat("HH'h':mm'm'")
    val calendarFormater = SimpleDateFormat("dd/MM/yyyy")
    fun getTime(time:Long): String{
        val cv = Calendar.getInstance()
        cv.timeInMillis = time
        return formater.format(cv.time)
    }

    fun getWorkOutTime(time:Long): String{
        val cv = Calendar.getInstance()
        cv.timeInMillis = time
        return wokOutformater.format(cv.time)
    }

    fun getCalendarFromDate(date: String): Calendar{
        return Calendar.getInstance().apply {
            time = calendarFormater.parse(date) as Date
        }
    }
    fun getDateFromCalendar(calendar: Calendar): String{
        return calendarFormater.format(calendar.time)
    }
    fun getCurrentDay():String{
        val cv = Calendar.getInstance()
        return calendarFormater.format(cv.time)
    }
}