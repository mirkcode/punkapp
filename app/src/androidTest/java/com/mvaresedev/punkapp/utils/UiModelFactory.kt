/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.utils

import com.mvaresedev.punkapp.domain.models.Beer
import com.mvaresedev.punkapp.domain.models.Ingredients
import com.mvaresedev.punkapp.domain.models.Method
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

object UiModelFactory {

    fun generateBeerList(size: Int): List<Beer> {
        val beerList = arrayListOf<Beer>()
        val counter = AtomicInteger(0)
        while(counter.get() < size) {
            beerList.add(generateBeer(counter.getAndIncrement()))
        }
        return beerList
    }


    private fun generateBeer(id: Int) : Beer {
        val firstBrewedCal = Calendar.getInstance()
        firstBrewedCal.setMonthYearInstance(2021, 0)
        return Beer(
            name = "beer$id",
            id = id,
            tagLine = "tagLine$id",
            ingredients = Ingredients(emptyList(), emptyList(), null),
            brewersTips = "brewersTips$id",
            attenuationLevel = 0.0f,
            abv = 0.0f,
            description = "description$id",
            ebc = 0.0f,
            boilVolume = "25 litres",
            volume = "25 litres",
            targetOg = 0.0f,
            ph = 5f,
            method = Method("80 celsius", emptyList(), null),
            imageUrl = null,
            ibu = null,
            foodPairing = emptyList(),
            firstBrewed = firstBrewedCal,
            srm = null,
            targetFg = 0.0f
        )
    }
}