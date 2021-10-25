package com.mvaresedev.punkapp.di

import com.mvaresedev.punkapp.data.db.mapper.DbMapperImpl
import com.mvaresedev.punkapp.data.network.mapper.NetworkMapperImpl
import com.mvaresedev.punkapp.data.repo.PunkRepositoryImpl
import com.mvaresedev.punkapp.domain.mapper.DbMapper
import com.mvaresedev.punkapp.domain.mapper.NetworkMapper
import com.mvaresedev.punkapp.domain.repo.PunkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    abstract fun bindDbMapper(dbMapperImpl: DbMapperImpl): DbMapper

    @Binds
    abstract fun bindNetworkMapper(networkMapperImpl: NetworkMapperImpl): NetworkMapper

    @Binds
    abstract fun bindRepository(repositoryImpl: PunkRepositoryImpl): PunkRepository

}