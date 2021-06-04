/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.punkapp.databinding.HolderBeerLoadingBinding

class BeerLoadingAdapter(private val retry: () -> Unit) : LoadStateAdapter<BeerLoadingAdapter.BeerLoadingVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BeerLoadingVH {
        val binding = HolderBeerLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerLoadingVH(binding, retry)
    }

    override fun onBindViewHolder(holder: BeerLoadingVH, loadState: LoadState) {
        holder.bindState(loadState)
    }

    class BeerLoadingVH(
        private val binding: HolderBeerLoadingBinding,
        retry: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bindState(loadState: LoadState) {
            binding.animationView.isVisible = loadState != LoadState.Loading
            binding.errorTxt.isVisible = loadState != LoadState.Loading
            binding.retryBtn.isVisible = loadState != LoadState.Loading
            binding.loadingPb.isVisible = loadState == LoadState.Loading
        }
    }

}