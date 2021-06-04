/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mvaresedev.punkapp.data.db.dao.BeerDao
import com.mvaresedev.punkapp.data.db.dao.RemotePageDao
import com.mvaresedev.punkapp.data.db.entities.BeerEntity
import com.mvaresedev.punkapp.data.db.entities.RemotePageEntity

@Database(entities = [BeerEntity::class, RemotePageEntity::class], version = 1, exportSchema = false)
@TypeConverters(DbTypeConverter::class)
abstract class PunkDatabase : RoomDatabase() {

    abstract fun getBeerDao(): BeerDao
    abstract fun getRemotePageDao(): RemotePageDao

    companion object {
        private const val DB_NAME = "punk_database"

        fun create(context: Context): PunkDatabase {
            return Room.databaseBuilder(
                context,
                PunkDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}