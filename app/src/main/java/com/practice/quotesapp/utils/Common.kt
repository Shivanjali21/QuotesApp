package com.practice.quotesapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimeStampToDMY(timestamp:Long):String {
  val sdf = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
  val date = Date(timestamp)
  return sdf.format(date)
}