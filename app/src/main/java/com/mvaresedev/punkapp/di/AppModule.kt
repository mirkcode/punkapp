/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.di

import com.mvaresedev.punkapp.BuildConfig
import com.mvaresedev.punkapp.data.db.PunkDatabase
import com.mvaresedev.punkapp.data.db.mapper.DbMapperImpl
import com.mvaresedev.punkapp.data.network.PunkApi
import com.mvaresedev.punkapp.data.network.mapper.NetworkMapperImpl
import com.mvaresedev.punkapp.domain.mapper.DbMapper
import com.mvaresedev.punkapp.domain.mapper.NetworkMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//config
private const val BASE_URL = "https://api.punkapi.com/v2/"
private const val CLIENT_TIMEOUT = 30L
//naming
private const val NAMED_CLIENT = "CLIENT"
private const val NAMED_RETROFIT = "RETROFIT"
const val NAMED_DB = "DB"
const val NAMED_API = "API"
const val NAMED_DB_MAPPER = "DB_MAPPER"
const val NAMED_NETWORK_MAPPER = "NETWORK_MAPPER"

val appModule = module {

    single(named(NAMED_CLIENT)) {
        val clientBuilder = OkHttpClient().newBuilder()
            .connectTimeout(CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
        }

        clientBuilder.build()
    }

    single(named(NAMED_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(named(NAMED_CLIENT)))
            .build()
    }

    single(named(NAMED_API)) {
        get<Retrofit>(named(NAMED_RETROFIT)).create(PunkApi::class.java)
    }

    single(named(NAMED_DB)) { PunkDatabase.create(androidContext()) }

    single<DbMapper>(named(NAMED_DB_MAPPER)) { DbMapperImpl() }

    single<NetworkMapper>(named(NAMED_NETWORK_MAPPER)) { NetworkMapperImpl() }
}