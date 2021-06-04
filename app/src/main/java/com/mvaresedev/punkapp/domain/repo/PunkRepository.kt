/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.domain.repo

import androidx.paging.PagingData
import com.mvaresedev.punkapp.domain.models.Beer
import com.mvaresedev.punkapp.ui.beer_list.models.BeerListFilter
import kotlinx.coroutines.flow.Flow

interface PunkRepository {
    fun getPagedBeers(filters: BeerListFilter): Flow<PagingData<Beer>>
}