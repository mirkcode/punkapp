/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvaresedev.punkapp.data.db.entities.RemotePageEntity

@Dao
interface RemotePageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemotePage(key: RemotePageEntity)

    @Query("SELECT * FROM remote_page WHERE startPeriod = :startPeriod AND endPeriod = :endPeriod ORDER BY id ASC")
    suspend fun getAllRemotePages(startPeriod: Long, endPeriod: Long): List<RemotePageEntity>

    @Query("DELETE FROM remote_page")
    suspend fun clearAllRemotePages()
}