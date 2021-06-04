/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mvaresedev.punkapp.data.db.PunkDatabase
import com.mvaresedev.punkapp.data.db.entities.BeerEntity
import com.mvaresedev.punkapp.data.db.entities.RemotePageEntity
import com.mvaresedev.punkapp.data.network.PunkApi
import com.mvaresedev.punkapp.domain.mapper.DbMapper
import com.mvaresedev.punkapp.domain.mapper.NetworkMapper
import com.mvaresedev.punkapp.ui.beer_list.models.BeerListFilter
import java.util.*

@ExperimentalPagingApi
class PunkRemoteMediator(
    private val punkApi: PunkApi,
    private val networkMapper: NetworkMapper,
    private val dbMapper: DbMapper,
    private val punkDatabase: PunkDatabase,
    private val initialPage: Int,
    private val filters: BeerListFilter
) : RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, BeerEntity>): MediatorResult {
        return try {
            if(loadType == LoadType.REFRESH) {
                punkDatabase.withTransaction {
                    punkDatabase.getBeerDao().clearAllBeers()
                    punkDatabase.getRemotePageDao().clearAllRemotePages()
                }
            }
            val startPeriod = filters.brewedAfter
            val endPeriod = filters.brewedBefore

            val pageSize = state.config.pageSize

            val pageNum: Int = when(loadType){
                LoadType.REFRESH -> initialPage
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastRemotePage = getLastRemotePage(startPeriod, endPeriod)
                    if(lastRemotePage != null)
                        lastRemotePage.page + 1
                    else
                        return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val listResponse = punkApi.getBeersList(pageNum, pageSize, networkMapper.parsePeriodParam(startPeriod), networkMapper.parsePeriodParam(endPeriod))
            val beerList = networkMapper.mapBeersResponseToDomain(listResponse)

            punkDatabase.withTransaction {
                punkDatabase.getBeerDao().insertAllBeers(dbMapper.mapBeerDomainToEntities(beerList))
                punkDatabase.getRemotePageDao().insertRemotePage(RemotePageEntity(0, pageNum, dbMapper.mapCalendarToPeriod(startPeriod), dbMapper.mapCalendarToPeriod(endPeriod)))
            }

            MediatorResult.Success(endOfPaginationReached = beerList.size < pageSize)

        } catch (exception: Exception) {
            exception.printStackTrace()
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getLastRemotePage(startPeriod: Calendar?, endPeriod: Calendar?): RemotePageEntity? {
        val startFilter = dbMapper.mapCalendarToPeriod(startPeriod)
        val endFilter = dbMapper.mapCalendarToPeriod(endPeriod)
        return punkDatabase.getRemotePageDao().getAllRemotePages(startFilter, endFilter).lastOrNull()
    }

}