/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Method(
    val fermentationTemperature: String,
    val mashTemp: List<MashTemp>,
    val twist: String?
): Parcelable