/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.domain.mapper

import com.mvaresedev.punkapp.data.network.models.BeersResponse
import com.mvaresedev.punkapp.domain.models.Beer
import java.util.*

interface NetworkMapper {
    fun mapBeersResponseToDomain(beersResponse: BeersResponse): List<Beer>
    fun parsePeriodParam(startPeriod: Calendar?): String?
}