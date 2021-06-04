/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvaresedev.punkapp.R
import com.mvaresedev.punkapp.databinding.HolderBeerBinding
import com.mvaresedev.punkapp.domain.models.Beer

class BeerPagingAdapter(private val onItemClick: (Beer) -> Unit) : PagingDataAdapter<Beer, BeerPagingAdapter.BeerVH>(
    DiffUtilCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerVH {
        val binding = HolderBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerVH(binding)
    }

    override fun onBindViewHolder(holder: BeerVH, position: Int) {
        getItem(position)?.let { holder.bindData(it, onItemClick) }
    }

    class BeerVH(private val binding: HolderBeerBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(beer: Beer, onItemClick: (Beer) -> Unit) {
            with(beer) {
                binding.nameTxt.text = name
                binding.tagLineTxt.text = tagLine
                Glide.with(binding.root).load(imageUrl).error(R.drawable.ic_beer_bottle).placeholder(R.drawable.ic_beer_bottle).into(binding.logoImg)
            }
            itemView.setOnClickListener { onItemClick(beer) }
        }
    }

    class DiffUtilCallback: DiffUtil.ItemCallback<Beer>() {

        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }
}