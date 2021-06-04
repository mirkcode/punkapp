/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvaresedev.punkapp.domain.models.Beer
import com.mvaresedev.punkapp.domain.models.Hop
import com.mvaresedev.punkapp.domain.models.Malt
import com.mvaresedev.punkapp.domain.models.MashTemp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BeerDetailViewModel: ViewModel() {

    private val df = SimpleDateFormat("MM/yyyy", Locale.ROOT)

    val beerChannel = Channel<Beer>(Channel.CONFLATED)

    val nameText by lazy { MutableLiveData<String>() }
    val tagLineText by lazy { MutableLiveData<String>() }
    val imageLogoUrl by lazy { MutableLiveData<String?>() }
    val abvText by lazy { MutableLiveData<String>() }
    val attenuationLevelText by lazy { MutableLiveData<String>() }
    val ebcText by lazy { MutableLiveData<String>() }
    val ebcVisible by lazy { MutableLiveData<Boolean>() }
    val srmText by lazy { MutableLiveData<String>() }
    val srmVisible by lazy { MutableLiveData<Boolean>() }
    val ibuText by lazy { MutableLiveData<String>() }
    val ibuVisible by lazy { MutableLiveData<Boolean>() }
    val phText by lazy { MutableLiveData<String>() }
    val phVisible by lazy { MutableLiveData<Boolean>() }
    val boilVolumeText by lazy { MutableLiveData<String>() }
    val volumeText by lazy { MutableLiveData<String>() }
    val firstBrewedText by lazy { MutableLiveData<String>() }
    val descriptionText by lazy { MutableLiveData<String>() }
    val targetFgText by lazy { MutableLiveData<String>() }
    val targetOgText by lazy { MutableLiveData<String>() }
    val maltItems by lazy { MutableLiveData<List<Malt>>() }
    val hopsItems by lazy { MutableLiveData<List<Hop>>() }
    val yeastText by lazy { MutableLiveData<String>() }
    val yeastVisible by lazy { MutableLiveData<Boolean>() }
    val mashTempItems by lazy { MutableLiveData<List<MashTemp>>() }
    val fermentationTempText by lazy { MutableLiveData<String>() }
    val twistText by lazy { MutableLiveData<String>() }
    val twistVisible by lazy { MutableLiveData<Boolean>() }
    val brewersTipsText by lazy { MutableLiveData<String>() }
    val foodPairingText by lazy { MutableLiveData<String>() }

    init {
        viewModelScope.launch {
            val beer = beerChannel.receive()
            showDetail(beer)
        }
    }

    private fun showDetail(beer: Beer) {
        nameText.postValue(beer.name)
        tagLineText.postValue(beer.tagLine)
        imageLogoUrl.postValue(beer.imageUrl)
        abvText.postValue("${beer.abv}%")
        attenuationLevelText.postValue(beer.attenuationLevel.toString())
        if(beer.ebc != null){
            ebcText.postValue(beer.ebc.toString())
            ebcVisible.postValue(true)
        } else {
            ebcVisible.postValue(false)
        }
        if(beer.srm != null){
            srmText.postValue(beer.srm.toString())
            srmVisible.postValue(true)
        } else {
            srmVisible.postValue(false)
        }
        if(beer.ibu != null){
            ibuText.postValue(beer.ibu.toString())
            ibuVisible.postValue(true)
        } else {
            ibuVisible.postValue(false)
        }
        if(beer.ph != null){
            phText.postValue(beer.ph.toString())
            phVisible.postValue(true)
        } else {
            phVisible.postValue(false)
        }
        volumeText.postValue(beer.volume)
        boilVolumeText.postValue(beer.boilVolume)
        firstBrewedText.postValue(df.format(beer.firstBrewed.time))
        descriptionText.postValue(beer.description)
        targetFgText.postValue(beer.targetFg.toString())
        targetOgText.postValue(beer.targetOg.toString())

        //ingredients
        maltItems.postValue(beer.ingredients.malt)
        hopsItems.postValue(beer.ingredients.hops)
        if(beer.ingredients.yeast != null){
            yeastText.postValue(beer.ingredients.yeast)
            yeastVisible.postValue(true)
        } else {
            yeastVisible.postValue(false)
        }

        //method
        mashTempItems.postValue(beer.method.mashTemp)
        fermentationTempText.postValue(beer.method.fermentationTemperature)
        if(beer.method.twist != null){
            twistText.postValue(beer.method.twist)
            twistVisible.postValue(true)
        } else {
            twistVisible.postValue(false)
        }

        //more
        brewersTipsText.postValue(beer.brewersTips)
        foodPairingText.postValue(beer.foodPairing.joinToString())
    }


}