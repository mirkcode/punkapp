/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.db.mapper

import com.mvaresedev.punkapp.data.db.entities.BeerEntity
import com.mvaresedev.punkapp.domain.mapper.DbMapper
import com.mvaresedev.punkapp.domain.models.Beer
import java.util.*
import javax.inject.Inject

class DbMapperImpl @Inject constructor(): DbMapper {

    override fun mapBeerEntityToDomain(beerEntity: BeerEntity): Beer {
        return with(beerEntity) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = firstBrewedDateMillis
            Beer(
                id = id,
                name = name,
                tagLine = tagLine,
                ingredients = ingredients,
                abv = abv,
                attenuationLevel = attenuationLevel,
                boilVolume = boilVolume,
                brewersTips = brewersTips,
                description = description,
                ebc = ebc,
                volume = volume,
                targetOg = targetOg,
                targetFg = targetFg,
                srm = srm,
                ph = ph,
                method = method,
                imageUrl = imageUrl,
                ibu = ibu,
                foodPairing = foodPairing,
                firstBrewed = calendar
            )
        }
    }

    override fun mapBeerDomainToEntities(beerList: List<Beer>): List<BeerEntity> {
        return beerList.map { beer ->
            with(beer){
                BeerEntity(
                    id = id,
                    name = name,
                    tagLine = tagLine,
                    ingredients = ingredients,
                    abv = abv,
                    attenuationLevel = attenuationLevel,
                    boilVolume = boilVolume,
                    brewersTips = brewersTips,
                    description = description,
                    ebc = ebc,
                    volume = volume,
                    targetOg = targetOg,
                    targetFg = targetFg,
                    srm = srm,
                    ph = ph,
                    method = method,
                    imageUrl = imageUrl,
                    ibu = ibu,
                    foodPairing = foodPairing,
                    firstBrewedDateMillis = firstBrewed.timeInMillis
                )
            }
        }
    }

    override fun mapCalendarToPeriod(calendar: Calendar?): Long {
        return calendar?.timeInMillis ?: -1
    }
}