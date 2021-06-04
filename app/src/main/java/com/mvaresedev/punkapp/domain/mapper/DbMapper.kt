/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.domain.mapper

import com.mvaresedev.punkapp.data.db.entities.BeerEntity
import com.mvaresedev.punkapp.domain.models.Beer
import java.util.*

interface DbMapper {
    fun mapBeerEntityToDomain(beerEntity: BeerEntity): Beer
    fun mapBeerDomainToEntities(beerList: List<Beer>): List<BeerEntity>
    fun mapCalendarToPeriod(calendar: Calendar?): Long
}