/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.repo

import androidx.paging.*
import com.mvaresedev.punkapp.data.PunkRemoteMediator
import com.mvaresedev.punkapp.data.db.PunkDatabase
import com.mvaresedev.punkapp.data.network.PunkApi
import com.mvaresedev.punkapp.domain.mapper.DbMapper
import com.mvaresedev.punkapp.domain.mapper.NetworkMapper
import com.mvaresedev.punkapp.domain.repo.PunkRepository
import com.mvaresedev.punkapp.ui.beer_list.models.BeerListFilter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PunkRepositoryImpl @Inject constructor(
    private val punkApi: PunkApi,
    private val punkDatabase: PunkDatabase,
    private val networkMapper: NetworkMapper,
    private val dbMapper: DbMapper
) : PunkRepository {

    companion object {
        private const val INITIAL_PAGE = 1
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 2
    }

    @ExperimentalPagingApi
    override fun getPagedBeers(filters: BeerListFilter) = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false, prefetchDistance = PREFETCH_DISTANCE),
            remoteMediator = PunkRemoteMediator(punkApi, networkMapper, dbMapper, punkDatabase, INITIAL_PAGE, filters),
            pagingSourceFactory = { punkDatabase.getBeerDao().getAllBeers() }
        ).flow.map { pagingData ->
            pagingData.map {  beerEntity ->
                dbMapper.mapBeerEntityToDomain(beerEntity)
            }
        }
}