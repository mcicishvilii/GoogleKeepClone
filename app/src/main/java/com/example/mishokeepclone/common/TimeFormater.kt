package com.example.mishokeepclone.common

import java.util.*


interface TimeFormater {
    fun formatMillisToDateAndSplit(millis:Long): String
}

class TimeFormaterIMPL() : TimeFormater {
    override fun formatMillisToDateAndSplit(millis: Long): String {
        val date = millis?.let { Date(it) }
        val dateWoYear = date.toString().substring(0, 10)
        val dateWithYear = date.toString().substring(30, 34)
        return "$dateWoYear $dateWithYear"
    }
}


