package com.example.cryptoapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToTime(timestamp: Long?): String {
    timestamp ?: return ""
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    val date = Date(timestamp * 1000)
    return sdf.format(date)
}