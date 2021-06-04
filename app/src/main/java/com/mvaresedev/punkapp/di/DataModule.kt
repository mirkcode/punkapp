/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.di

import com.mvaresedev.punkapp.data.repo.PunkRepositoryImpl
import com.mvaresedev.punkapp.domain.repo.PunkRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NAMED_REPO = "REPO"

val dataModule = module {

    single<PunkRepository>(named(NAMED_REPO)) {
        PunkRepositoryImpl(
            get(named(NAMED_API)),
            get(named(NAMED_DB)),
            get(named(NAMED_NETWORK_MAPPER)),
            get(named(NAMED_DB_MAPPER))
        )
    }
}