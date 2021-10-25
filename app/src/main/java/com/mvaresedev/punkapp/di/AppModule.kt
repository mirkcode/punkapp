/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.di

import android.content.Context
import com.mvaresedev.punkapp.data.db.PunkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PunkDatabase {
        return PunkDatabase.create(context)
    }

}