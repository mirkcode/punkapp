/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.mvaresedev.punkapp.domain.repo.PunkRepository
import com.mvaresedev.punkapp.ui.beer_list.models.BeerListFilter
import com.mvaresedev.punkapp.utils.Event
import com.mvaresedev.punkapp.utils.setMonthYearInstance
import com.mvaresedev.punkapp.utils.CoroutinesTestExtension
import com.mvaresedev.punkapp.utils.InstantExecutorExtension
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
class BeerListViewModelTest {

    private val repository: PunkRepository = mock()
    private val savedStateHandle: SavedStateHandle = mock {
        on { getLiveData<BeerListFilter>(FILTERS_KEY) } doReturn MutableLiveData(defaultBeerListFilter)
        on { get<BeerListFilter>(FILTERS_KEY)} doReturn defaultBeerListFilter
    }

    private val brewedAfterTextObserver: Observer<String?> = mock()
    private val brewedBeforeTextObserver: Observer<String?> = mock()
    private val startFilterSelectionObserver: Observer<Event<Calendar?>> = mock()
    private val endFilterSelectionObserver: Observer<Event<Calendar?>> = mock()

    private val viewModel = BeerListViewModel(
            repository,
            savedStateHandle
    )

    @BeforeEach
    fun setUp() {
        viewModel.brewedAfterText.observeForever(brewedAfterTextObserver)
        viewModel.brewedBeforeText.observeForever(brewedBeforeTextObserver)
        viewModel.startFilterSelection.observeForever(startFilterSelectionObserver)
        viewModel.endFilterSelection.observeForever(endFilterSelectionObserver)
    }

    @Test
    fun `SHOULD update filter brewed after date WHEN brewed after date is changed ON startPeriodFilterSelected`() = runBlocking {
        val expectedStartDate = "12/2021"
        viewModel.onStartPeriodFilterSelected(2021, 11)

        verify(brewedAfterTextObserver, times(1)).onChanged(expectedStartDate)
    }

    @Test
    fun `SHOULD do nothing WHEN brewed after date is not changed ON startPeriodFilterSelected`() = runBlocking {
        viewModel.onStartPeriodFilterSelected(2021, 0)

        verifyZeroInteractions(brewedAfterTextObserver)
    }

    @Test
    fun `SHOULD fire start filter selection with date WHEN current filter is not null ON afterFilterClick`() {
        viewModel.afterFilterClick()

        verify(startFilterSelectionObserver, times(1)).onChanged(eq(Event(defaultBeerListFilter.brewedAfter)))
    }

    @Test
    fun `SHOULD fire end filter selection with date WHEN current filter is not null ON beforeFilterClick`() {
        viewModel.beforeFilterClick()

        verify(endFilterSelectionObserver, times(1)).onChanged(eq(Event(defaultBeerListFilter.brewedBefore)))
    }

    companion object {
        const val FILTERS_KEY = "FILTERS_KEY"

        val defaultBeerListFilter = BeerListFilter(
                brewedAfter = Calendar.getInstance().apply { setMonthYearInstance(2021, 0) },
                brewedBefore = Calendar.getInstance().apply { setMonthYearInstance(2021, 3) },
        )
    }
}