/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.utils

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.binaryfork.spanny.Spanny
import com.mvaresedev.punkapp.R
import java.util.*

fun Calendar.setMonthYearInstance(year: Int, month: Int) {
    this.set(Calendar.YEAR, year)
    this.set(Calendar.MONTH, month)
    this.set(Calendar.DAY_OF_MONTH, 1) //ignoring day selection
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
}

fun Calendar.isSameOf(newCalendar: Calendar): Boolean {
    return this.timeInMillis == newCalendar.timeInMillis
}

fun TextView.setBoldSpannedText(@StringRes labelRes: Int, text: String) {
    val context = this.context
    this.text = Spanny(context.getString(labelRes))
            .append(" ")
            .append(text, CustomTypefaceSpan(ResourcesCompat.getFont(context, R.font.montserrat_alternates_bold)))
}