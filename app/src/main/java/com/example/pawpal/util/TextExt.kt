package com.example.pawpal.util

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun TextView?.inputText(): String {
    return this?.text?.toString() ?: ""
}

fun Int.getFragmentName(context : Context) : String{
    return context.resources.getString(this)
}

val ViewGroup.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)

val String.toEditText: Editable get() = Editable.Factory.getInstance().newEditable(this)

val String.firstLetter: String
    get() = this.substring(0, 1)

fun String.getFullDate(): Date {
    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)
    return simpleDateFormat.parse(this)!!
}


fun Date.getStringDate(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    return formatter.format(this)
}

fun Date.getStringOnlyDate(): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(this)
}

fun Long.getStringOnlyTime(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(this)
}

fun formatted(number: Int) = if (number < 10) "0${number}" else "$number"