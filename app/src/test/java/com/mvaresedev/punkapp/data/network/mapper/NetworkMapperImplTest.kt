/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.network.mapper

import com.mvaresedev.punkapp.domain.mapper.NetworkMapper
import com.mvaresedev.punkapp.utils.ModelFactory
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NetworkMapperImplTest {

    private lateinit var networkMapper: NetworkMapper

    @BeforeEach
    fun setUp() {
        networkMapper = NetworkMapperImpl()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `SHOULD map correct beerList WHEN response model is obtained ON mapBeersResponseToDomain`()  {

        val expectedBeerList = ModelFactory.generateBeerList(100)
        val response = ModelFactory.createResponse(expectedBeerList)

        val resultList = networkMapper.mapBeersResponseToDomain(response)

        assertEquals(resultList, expectedBeerList)
    }
}