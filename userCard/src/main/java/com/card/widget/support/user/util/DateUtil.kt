package com.oplus.support.util

import com.oplus.cardwidget.util.Logger
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun generateFormatDate(timeInMills: Long): String {
        try {
            val date = Date(timeInMills)
            val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.US)
            return sdf.format(date)
        } catch (e: Exception) {
            Logger.e("", "generateFormatDate $e")
        }
        return ""
    }
}