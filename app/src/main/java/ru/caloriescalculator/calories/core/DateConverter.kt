package ru.caloriescalculator.calories.core

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val DATE_FORMAT = "dd.MM.yyyy"

class DateConverter @Inject constructor() {

    fun convertToString(date: Date): String {
        return DateFormat.format(DATE_FORMAT, date).toString()
    }

    fun convertToDate(dateString: String): Date {
        val format = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return try {
            format.parse(dateString)
        } catch (_: Exception) {
            Date()
        }
    }
}