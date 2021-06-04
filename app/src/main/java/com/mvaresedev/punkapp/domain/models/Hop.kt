/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hop(
    val add: String,
    val amount: String,
    val attribute: String,
    val name: String
): Parcelable