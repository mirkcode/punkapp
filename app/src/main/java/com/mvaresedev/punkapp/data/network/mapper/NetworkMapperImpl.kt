/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.network.mapper

import com.mvaresedev.punkapp.data.network.models.BeersResponse
import com.mvaresedev.punkapp.domain.mapper.NetworkMapper
import com.mvaresedev.punkapp.domain.models.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NetworkMapperImpl @Inject constructor(): NetworkMapper {

    private val dateFormat = SimpleDateFormat("MM/yyyy", Locale.ROOT)

    override fun mapBeersResponseToDomain(beersResponse: BeersResponse): List<Beer> {
        return beersResponse.map { item ->
            with(item) {
                Beer(
                    id = id,
                    name = name.trim(),
                    tagLine = tagLine,
                    ingredients = parseIngredients(ingredients),
                    abv = abv,
                    attenuationLevel = attenuationLevel,
                    brewersTips = brewersTips,
                    description = description,
                    boilVolume = parseUnitValue(boilVolume),
                    ebc = ebc,
                    firstBrewed = parseFirstBrewed(firstBrewed),
                    foodPairing = foodPairing,
                    ibu = ibu,
                    imageUrl = imageUrl,
                    method = parseMethod(method),
                    ph = ph,
                    srm = srm,
                    targetFg = targetFg,
                    targetOg = targetOg,
                    volume = parseUnitValue(volume)
                )
            }
        }
    }

    override fun parsePeriodParam(calendar: Calendar?): String? {
        return if(calendar != null) {
            dateFormat.format(calendar.time)
        } else {
            null
        }
    }

    private fun parseMethod(response: BeersResponse.BeersResponseItem.MethodResponse): Method {

        return Method(
            fermentationTemperature = parseUnitValue(response.fermentation.temp),
            mashTemp = response.mashTemp.map { item ->
                MashTemp(item.duration, parseUnitValue(item.temp))
            },
            twist = response.twist
        )
    }

    private fun parseFirstBrewed(dateString: String): Calendar {
        val date = try {
            dateFormat.parse(dateString)
        } catch (ex: Exception) {
            null
        }
        val calendar = Calendar.getInstance()
        date?.let {
            calendar.time = date
        }
        return calendar
    }

    private fun parseUnitValue(unitValueResponse: BeersResponse.BeersResponseItem.UnitValueResponse): String {
        return "${unitValueResponse.value} ${unitValueResponse.unit}"
    }

    private fun parseAmount(unitValueResponse: BeersResponse.BeersResponseItem.AmountResponse): String {
        return "${unitValueResponse.value} ${unitValueResponse.unit}"
    }

    private fun parseIngredients(response: BeersResponse.BeersResponseItem.IngredientResponse): Ingredients {
        return Ingredients(
            hops = response.hops.map { Hop(it.add, parseAmount(it.amount), it.attribute, it.name) },
            malt = response.malt.map { Malt(it.name, parseAmount(it.amount)) },
            yeast = response.yeast
        )
    }
}