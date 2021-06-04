/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.network.models

import com.google.gson.annotations.SerializedName

class BeersResponse : ArrayList<BeersResponse.BeersResponseItem>(){
    data class BeersResponseItem(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("abv")
        val abv: Float,
        @SerializedName("attenuation_level")
        val attenuationLevel: Float,
        @SerializedName("boil_volume")
        val boilVolume: UnitValueResponse,
        @SerializedName("brewers_tips")
        val brewersTips: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("ebc")
        val ebc: Float?,
        @SerializedName("first_brewed")
        val firstBrewed: String,
        @SerializedName("food_pairing")
        val foodPairing: List<String>,
        @SerializedName("ibu")
        val ibu: Float?,
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("ingredients")
        val ingredients: IngredientResponse,
        @SerializedName("method")
        val method: MethodResponse,
        @SerializedName("ph")
        val ph: Float?,
        @SerializedName("srm")
        val srm: Float?,
        @SerializedName("tagline")
        val tagLine: String,
        @SerializedName("target_fg")
        val targetFg: Float,
        @SerializedName("target_og")
        val targetOg: Float,
        @SerializedName("volume")
        val volume: UnitValueResponse
    ) {
        data class MethodResponse(
            @SerializedName("fermentation")
            val fermentation: Fermentation,
            @SerializedName("mash_temp")
            val mashTemp: List<MashTemp>,
            @SerializedName("twist")
            val twist: String?
        ) {
            data class Fermentation(
                @SerializedName("temp")
                val temp: UnitValueResponse
            )

            data class MashTemp(
                @SerializedName("duration")
                val duration: Int?,
                @SerializedName("temp")
                val temp: UnitValueResponse
            )
        }

        data class IngredientResponse(
            @SerializedName("hops")
            val hops: List<Hop>,
            @SerializedName("malt")
            val malt: List<Malt>,
            @SerializedName("yeast")
            val yeast: String?
        ) {
            data class Hop(
                @SerializedName("add")
                val add: String,
                @SerializedName("amount")
                val amount: AmountResponse,
                @SerializedName("attribute")
                val attribute: String,
                @SerializedName("name")
                val name: String
            )

            data class Malt(
                @SerializedName("amount")
                val amount: AmountResponse,
                @SerializedName("name")
                val name: String
            )
        }

        data class UnitValueResponse(
            @SerializedName("unit")
            val unit: String,
            @SerializedName("value")
            val value: Int
        )

        data class AmountResponse(
            @SerializedName("unit")
            val unit: String,
            @SerializedName("value")
            val value: Float
        )
    }
}