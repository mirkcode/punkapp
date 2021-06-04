/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredients(
    val hops: List<Hop>,
    val malt: List<Malt>,
    val yeast: String?
): Parcelable