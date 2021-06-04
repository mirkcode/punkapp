/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.punkapp.R
import com.mvaresedev.punkapp.databinding.HolderHopBinding
import com.mvaresedev.punkapp.domain.models.Hop
import com.mvaresedev.punkapp.utils.setBoldSpannedText

class HopListAdapter : RecyclerView.Adapter<HopListAdapter.HopVH>() {

    private val hops = ArrayList<Hop>()

    fun submitData(newItems: List<Hop>) {
        this.hops.clear()
        this.hops.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HopVH {
        val binding = HolderHopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HopVH(binding)
    }

    override fun onBindViewHolder(holder: HopVH, position: Int) {
        holder.bindData(hops[position])
    }

    override fun getItemCount() = hops.size

    class HopVH(private val binding: HolderHopBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(hop: Hop) {
            binding.hopNameTxt.text = hop.name
            binding.hopAddTxt.setBoldSpannedText(R.string.detail_hop_add, hop.add)
            binding.hopAmountTxt.setBoldSpannedText(R.string.detail_hop_amount, hop.amount)
            binding.hopAttributeTxt.setBoldSpannedText(R.string.detail_hop_attribute, hop.attribute)
        }
    }
}