/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_page")
data class RemotePageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val page: Int,
    val startPeriod: Long,
    val endPeriod: Long
)