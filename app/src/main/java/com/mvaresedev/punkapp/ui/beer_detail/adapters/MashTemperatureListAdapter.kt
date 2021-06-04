/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.punkapp.R
import com.mvaresedev.punkapp.databinding.HolderMashTemperatureBinding
import com.mvaresedev.punkapp.domain.models.MashTemp
import com.mvaresedev.punkapp.utils.setBoldSpannedText

class MashTemperatureListAdapter : RecyclerView.Adapter<MashTemperatureListAdapter.MashTempVH>() {

    private val mashTemperatureList = ArrayList<MashTemp>()

    fun submitData(newItems: List<MashTemp>) {
        this.mashTemperatureList.clear()
        this.mashTemperatureList.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MashTempVH {
        val binding = HolderMashTemperatureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MashTempVH(binding)
    }

    override fun onBindViewHolder(holder: MashTempVH, position: Int) {
        holder.bindData(mashTemperatureList[position])
    }

    override fun getItemCount() = mashTemperatureList.size

    class MashTempVH(private val binding: HolderMashTemperatureBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(mashTemp: MashTemp) {
            binding.temperatureTxt.text = mashTemp.temperature
            if(mashTemp.duration != null) {
                binding.durationTxt.isVisible = true
                binding.durationTxt.setBoldSpannedText(R.string.detail_item_duration, mashTemp.duration.toString())
            } else {
                binding.durationTxt.isVisible = false
            }
        }
    }
}