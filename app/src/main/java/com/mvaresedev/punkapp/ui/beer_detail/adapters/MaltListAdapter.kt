/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.punkapp.databinding.HolderMaltBinding
import com.mvaresedev.punkapp.domain.models.Malt

class MaltListAdapter : RecyclerView.Adapter<MaltListAdapter.MaltVH>() {

    private val maltList = ArrayList<Malt>()

    fun submitData(newItems: List<Malt>) {
        this.maltList.clear()
        this.maltList.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaltVH {
        val binding = HolderMaltBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaltVH(binding)
    }

    override fun onBindViewHolder(holder: MaltVH, position: Int) {
        holder.bindData(maltList[position])
    }

    override fun getItemCount() = maltList.size

    class MaltVH(private val binding: HolderMaltBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(malt: Malt) {
            binding.maltNameTxt.text = malt.name
            binding.maltAmountTxt.text = malt.amount
        }
    }
}