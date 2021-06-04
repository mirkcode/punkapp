/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_list

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.mvaresedev.punkapp.domain.models.Beer
import com.mvaresedev.punkapp.domain.repo.PunkRepository
import com.mvaresedev.punkapp.ui.beer_list.models.BeerListFilter
import com.mvaresedev.punkapp.utils.Event
import com.mvaresedev.punkapp.utils.isSameOf
import com.mvaresedev.punkapp.utils.setMonthYearInstance
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class BeerListViewModel(
    private val repository: PunkRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val TAG = "BeerListViewModel"
        private const val FILTERS_KEY = "FILTERS_KEY"
        private val DEFAULT_FILTERS = BeerListFilter(null, null)
    }

    private val df = SimpleDateFormat("MM/yyyy", Locale.ROOT)

    val loadStateChannel = Channel<CombinedLoadStates>(Channel.UNLIMITED)
    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    val brewedBeforeText by lazy { MutableLiveData<String?>() }
    val brewedAfterText by lazy { MutableLiveData<String?>() }
    val loadingVisible by lazy { MutableLiveData<Boolean>() }
    val errorVisible by lazy { MutableLiveData<Boolean>() }
    val startFilterSelection by lazy { MutableLiveData<Event<Calendar?>>() }
    val endFilterSelection by lazy { MutableLiveData<Event<Calendar?>>() }
    val navigationDetail by lazy { MutableLiveData<Event<Beer>>() }

    val beersFlow = savedStateHandle.getLiveData<BeerListFilter>(FILTERS_KEY)
            .asFlow()
            .flatMapLatest { filters -> repository.getPagedBeers(filters).cachedIn(viewModelScope) }
            .cachedIn(viewModelScope)

    init {
        if (!savedStateHandle.contains(FILTERS_KEY))
            savedStateHandle.set(FILTERS_KEY, DEFAULT_FILTERS)

        viewModelScope.launch {
            loadStateChannel.consumeAsFlow().collect { loadState ->
                handleLoadStateChange(loadState)
            }
        }
    }

    private fun handleLoadStateChange(loadState: CombinedLoadStates) {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                errorVisible.postValue(false)
                loadingVisible.postValue(true)
            }
            is LoadState.Error -> {
                errorVisible.postValue(true)
                loadingVisible.postValue(false)
            }
            else -> {
                errorVisible.postValue(false)
                loadingVisible.postValue(false)
            }
        }
    }

    fun onStartPeriodFilterSelected(year: Int, monthOfYear: Int) {
        val currentFilters = savedStateHandle.get<BeerListFilter>(FILTERS_KEY)
        val newCal = Calendar.getInstance()
        newCal.setMonthYearInstance(year, monthOfYear)
        updateFilters(newCal, currentFilters?.brewedBefore)
    }

    fun onEndPeriodFilterSelected(year: Int, monthOfYear: Int) {
        val currentFilters = savedStateHandle.get<BeerListFilter>(FILTERS_KEY)
        val newCal = Calendar.getInstance()
        newCal.setMonthYearInstance(year, monthOfYear)
        updateFilters(currentFilters?.brewedAfter, newCal)
    }

    private fun areFiltersChanged(startPeriod: Calendar?, endPeriod: Calendar?): Boolean {
        val currentFilters = savedStateHandle.get<BeerListFilter>(FILTERS_KEY)
        val currentStartPeriod = currentFilters?.brewedAfter
        val currentEndPeriod = currentFilters?.brewedBefore

        Log.d(TAG, "newStart $startPeriod, currentStart $currentStartPeriod, newEnd $endPeriod, currentEnd $currentEndPeriod")

        if((startPeriod != null && currentStartPeriod == null) || (startPeriod == null && currentStartPeriod != null)) {
            return true
        }
        if((endPeriod != null && currentEndPeriod == null) || (endPeriod == null && currentEndPeriod != null)) {
            return true
        }
        if(startPeriod != null && currentStartPeriod != null && !startPeriod.isSameOf(currentStartPeriod)) {
            return true
        }
        if(endPeriod != null && currentEndPeriod != null && !endPeriod.isSameOf(currentEndPeriod)) {
            return true
        }

        return false
    }

    private fun updateFilters(startPeriod: Calendar?, endPeriod: Calendar?) {
        if (!areFiltersChanged(startPeriod, endPeriod)) return

        clearListCh.offer(Unit)
        savedStateHandle.set(FILTERS_KEY, BeerListFilter(startPeriod, endPeriod))
        brewedAfterText.postValue(if(startPeriod != null) df.format(startPeriod.time) else null)
        brewedBeforeText.postValue(if(endPeriod != null) df.format(endPeriod.time) else null)
    }

    fun afterFilterClick() {
        val currentFilters = savedStateHandle.get<BeerListFilter>(FILTERS_KEY)
        currentFilters?.let {
            if(it.brewedAfter != null)
                startFilterSelection.postValue(Event(it.brewedAfter))
            else
                startFilterSelection.postValue(Event(Calendar.getInstance()))
        }
    }

    fun beforeFilterClick() {
        val currentFilters = savedStateHandle.get<BeerListFilter>(FILTERS_KEY)
        currentFilters?.let {
            if(it.brewedBefore != null)
                endFilterSelection.postValue(Event(it.brewedBefore))
            else
                endFilterSelection.postValue(Event(Calendar.getInstance()))
        }
    }

    fun clearFilterClick() {
        updateFilters(null, null)
    }

    fun onBeerItemClick(beer: Beer) {
        navigationDetail.postValue(Event(beer))
    }

}