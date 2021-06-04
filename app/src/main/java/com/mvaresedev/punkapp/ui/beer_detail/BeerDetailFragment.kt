/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_detail

import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.binaryfork.spanny.Spanny
import com.bumptech.glide.Glide
import com.mvaresedev.punkapp.R
import com.mvaresedev.punkapp.databinding.FragmentBeerDetailBinding
import com.mvaresedev.punkapp.ui.beer_detail.adapters.HopListAdapter
import com.mvaresedev.punkapp.ui.beer_detail.adapters.MaltListAdapter
import com.mvaresedev.punkapp.ui.beer_detail.adapters.MashTemperatureListAdapter
import com.mvaresedev.punkapp.utils.CustomTypefaceSpan
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeerDetailFragment: Fragment() {

    private val args: BeerDetailFragmentArgs by navArgs()
    private val viewModel by viewModel<BeerDetailViewModel>()

    private val maltAdapter by lazy { MaltListAdapter() }
    private val hopsAdapter by lazy { HopListAdapter() }
    private val mashTempAdapter by lazy { MashTemperatureListAdapter() }

    private var _binding: FragmentBeerDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBeerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeViewModel()

        lifecycleScope.launchWhenCreated {
            viewModel.beerChannel.send(args.beer)
        }
    }

    private fun setupUi() {
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.maltRv.adapter = maltAdapter
        binding.hopsRv.adapter = hopsAdapter
        binding.mashTemperaturesRv.adapter = mashTempAdapter
    }

    private fun observeViewModel() {
        with(viewModel) {
            nameText.observe(viewLifecycleOwner, { text ->
                binding.nameTxt.text = text
            })
            tagLineText.observe(viewLifecycleOwner, { text ->
                binding.tagLineTxt.text = text
            })
            imageLogoUrl.observe(viewLifecycleOwner, { imageUrl ->
                Glide.with(binding.root).load(imageUrl).error(R.drawable.ic_beer_bottle).placeholder(R.drawable.ic_beer_bottle).into(binding.logoImg)
            })
            abvText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.abvTxt, R.string.detail_abv, text)
            })
            attenuationLevelText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.attenuationLevelTxt, R.string.detail_attenuation_level, text)
            })
            ebcVisible.observe(viewLifecycleOwner, { visible ->
                binding.ebcTxt.isVisible = visible
            })
            ebcText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.ebcTxt, R.string.detail_ebc, text)
            })
            srmVisible.observe(viewLifecycleOwner, { visible ->
                binding.srmTxt.isVisible = visible
            })
            srmText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.srmTxt, R.string.detail_srm, text)
            })
            ibuVisible.observe(viewLifecycleOwner, { visible ->
                binding.ibuTxt.isVisible = visible
            })
            ibuText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.ibuTxt, R.string.detail_ibu, text)
            })
            phVisible.observe(viewLifecycleOwner, { visible ->
                binding.phTxt.isVisible = visible
            })
            phText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.phTxt, R.string.detail_ph, text)
            })
            boilVolumeText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.boilVolumeTxt, R.string.detail_boil_volume, text)
            })
            volumeText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.volumeTxt, R.string.detail_volume, text)
            })
            firstBrewedText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.firstBrewedTxt, R.string.detail_first_brewed, text)
            })
            descriptionText.observe(viewLifecycleOwner, { text ->
                binding.descriptionTxt.text = text
            })
            targetFgText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.targetFgTxt, R.string.detail_target_fg, text)
            })
            targetOgText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.targetOgTxt, R.string.detail_target_og, text)
            })
            maltItems.observe(viewLifecycleOwner, { items ->
                maltAdapter.submitData(items)
            })
            hopsItems.observe(viewLifecycleOwner, { items ->
                hopsAdapter.submitData(items)
            })
            yeastVisible.observe(viewLifecycleOwner, { visible ->
                binding.yeastTxt.isVisible = visible
            })
            yeastText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.yeastTxt, R.string.detail_yeast, text)
            })
            mashTempItems.observe(viewLifecycleOwner, { items ->
                mashTempAdapter.submitData(items)
            })
            fermentationTempText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.fermentationTempTxt, R.string.detail_fermentation_temperature, text)
            })
            twistVisible.observe(viewLifecycleOwner, { visible ->
                binding.twistTxt.isVisible = visible
            })
            twistText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.twistTxt, R.string.detail_twist, text)
            })
            brewersTipsText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.brewersTipsTxt, R.string.detail_brewers_tips, text)
            })
            foodPairingText.observe(viewLifecycleOwner, { text ->
                setSpannedText(binding.foodPairingTxt, R.string.detail_food_pairing, text)
            })
        }
    }

    private fun setSpannedText(textView: TextView, @StringRes labelRes: Int, text: String) {
        textView.text = Spanny(getString(labelRes))
                .append(" ")
                .append(text,
                        CustomTypefaceSpan(ResourcesCompat.getFont(requireContext(), R.font.montserrat_alternates_bold)),
                        ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                )
    }
}