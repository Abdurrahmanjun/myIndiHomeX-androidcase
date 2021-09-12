package com.abdurrahmanjun.androidcase.presentation.utils

import android.view.View
import android.widget.ProgressBar
import java.text.SimpleDateFormat
import java.util.*

class ViewUtils {

    companion object {

        fun getDate(milliSeconds: Long, dateFormat: String?): String? {
            // Create a DateFormatter object for displaying date in specified format.
            val formatter = SimpleDateFormat(dateFormat)

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar: Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(milliSeconds)
            return formatter.format(calendar.getTime())
        }

    }

}