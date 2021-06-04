/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.di

import androidx.lifecycle.SavedStateHandle
import com.mvaresedev.punkapp.ui.beer_detail.BeerDetailViewModel
import com.mvaresedev.punkapp.ui.beer_list.BeerListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

//naming
private const val NAMED_STATE_HANDLE = "SAVED_STATE_HANDLE"

@ExperimentalCoroutinesApi
val uiModule = module {

    factory(named(NAMED_STATE_HANDLE)) { SavedStateHandle() }

    viewModel { BeerListViewModel(get(named(NAMED_REPO)), get(named(NAMED_STATE_HANDLE))) }
    viewModel { BeerDetailViewModel() }
}