/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_list.models

import java.io.Serializable
import java.util.*

data class BeerListFilter(val brewedAfter: Calendar?, val brewedBefore: Calendar?): Serializable