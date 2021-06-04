/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Beer(
    val id: Int,
    val name: String,
    val tagLine: String,
    val ingredients: Ingredients,
    val abv: Float,
    val attenuationLevel: Float,
    val boilVolume: String,
    val brewersTips: String,
    val description: String,
    val ebc: Float?,
    val firstBrewed: Calendar,
    val foodPairing: List<String>,
    val ibu: Float?,
    val imageUrl: String?,
    val method: Method,
    val ph: Float?,
    val srm: Float?,
    val targetFg: Float,
    val targetOg: Float,
    val volume: String
): Parcelable