/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mvaresedev.punkapp.domain.models.Ingredients
import com.mvaresedev.punkapp.domain.models.Method

@Entity(tableName = "beers")
data class BeerEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val abv: Float,
    val attenuationLevel: Float,
    val boilVolume: String,
    val brewersTips: String,
    val description: String,
    val ebc: Float?,
    val firstBrewedDateMillis: Long,
    val foodPairing: List<String>,
    val ibu: Float?,
    val imageUrl: String?,
    @Embedded val ingredients: Ingredients,
    @Embedded val method: Method,
    val ph: Float?,
    val srm: Float?,
    val tagLine: String,
    val targetFg: Float,
    val targetOg: Float,
    val volume: String
)