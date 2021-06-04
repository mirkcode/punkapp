/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mvaresedev.punkapp.domain.models.Hop
import com.mvaresedev.punkapp.domain.models.Malt
import com.mvaresedev.punkapp.domain.models.MashTemp

class DbTypeConverter {
    private val gson = Gson()

    //MashTemp
    @TypeConverter
    fun fromMashTempListToJson(list: List<MashTemp>): String {
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromJsonToMashTempList(json: String): List<MashTemp> {
        val listType = object : TypeToken<List<MashTemp>>() {}.type
        return gson.fromJson(json, listType)
    }

    //IngredientResponse.Hop
    @TypeConverter
    fun fromHopListToJson(list: List<Hop>): String {
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromJsonToHopList(json: String): List<Hop> {
        val listType = object : TypeToken<List<Hop>>() {}.type
        return gson.fromJson(json, listType)
    }

    //IngredientResponse.Malt
    @TypeConverter
    fun fromMaltListToJson(list: List<Malt>): String {
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromJsonToMaltList(json: String): List<Malt> {
        val listType = object : TypeToken<List<Malt>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun fromListToJson(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromJsonToList(json: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, listType)
    }
}