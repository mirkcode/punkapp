/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.network

import com.mvaresedev.punkapp.data.network.models.BeersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkApi {
    @GET("beers")
    suspend fun getBeersList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("brewed_after") brewedAfter: String?,
        @Query("brewed_before") brewedBefore: String?
    ): BeersResponse
}