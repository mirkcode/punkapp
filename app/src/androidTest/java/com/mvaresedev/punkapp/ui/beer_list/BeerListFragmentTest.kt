/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.paging.PagingData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mvaresedev.punkapp.R
import com.mvaresedev.punkapp.di.NAMED_REPO
import com.mvaresedev.punkapp.di.appModule
import com.mvaresedev.punkapp.di.uiModule
import com.mvaresedev.punkapp.domain.repo.PunkRepository
import com.mvaresedev.punkapp.ui.beer_list.adapters.BeerPagingAdapter
import com.mvaresedev.punkapp.ui.beer_list.models.BeerListFilter
import com.mvaresedev.punkapp.utils.UiModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class BeerListFragmentTest: KoinTest {

    lateinit var mockedRepository: PunkRepository
    lateinit var mockDataModule: Module

    @Before
    fun setup() {
        mockedRepository = Mockito.mock(PunkRepository::class.java)
        mockDataModule = module {
            single(named(NAMED_REPO), override = true) { mockedRepository }
        }

        loadKoinModules(listOf(appModule, mockDataModule, uiModule))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testRetrieveBeerListUseCase() {
        Mockito.`when`(mockedRepository.getPagedBeers(DEFAULT_FILTERS))
            .thenReturn(flowOf(PagingData.from(UiModelFactory.generateBeerList(10))))

        launchFragmentInContainer<BeerListFragment>(null, R.style.AppTheme)

        onView(withId(R.id.error_container)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testScrollToPositionUseCase() {
        Mockito.`when`(mockedRepository.getPagedBeers(DEFAULT_FILTERS))
            .thenReturn(flowOf(PagingData.from(UiModelFactory.generateBeerList(20))))

        launchFragmentInContainer<BeerListFragment>(null, R.style.AppTheme)

        onView(withId(R.id.beers_rv)).perform(
                RecyclerViewActions.scrollToPosition<BeerPagingAdapter.BeerVH>(19)
            )

        onView(withText("beer19")).check(matches(isCompletelyDisplayed()))
    }

    @After
    fun tearDown() {
        unloadKoinModules(listOf(appModule, mockDataModule, uiModule))
    }

    companion object {
        private val DEFAULT_FILTERS = BeerListFilter(null, null)
    }
}