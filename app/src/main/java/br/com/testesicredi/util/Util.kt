package br.com.testesicredi.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class Util {

    fun convertDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateConverted = Date(TimeUnit.SECONDS.toMillis(date))

        return dateFormat.format(dateConverted)
    }
}