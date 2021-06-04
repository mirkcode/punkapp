/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvaresedev.punkapp.data.db.entities.BeerEntity

@Dao
interface BeerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBeers(beerList: List<BeerEntity>)

    @Query("SELECT * FROM beers")
    fun getAllBeers(): PagingSource<Int, BeerEntity>

    @Query("DELETE FROM beers")
    suspend fun clearAllBeers()
}