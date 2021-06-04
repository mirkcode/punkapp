/*
 * Copyright 2021. Mirko Varese
 */

package com.mvaresedev.punkapp.ui.beer_list

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mvaresedev.punkapp.R
import com.mvaresedev.punkapp.databinding.FragmentBeerListBinding
import com.mvaresedev.punkapp.domain.models.Beer
import com.mvaresedev.punkapp.ui.beer_list.adapters.BeerLoadingAdapter
import com.mvaresedev.punkapp.ui.beer_list.adapters.BeerPagingAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

@ExperimentalCoroutinesApi
class BeerListFragment : Fragment() {

    private val viewModel by viewModel<BeerListViewModel>()
    private val adapter by lazy { BeerPagingAdapter(::onItemCLick) }

    private var _binding: FragmentBeerListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeerListBinding.inflate(inflater, container, false)
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
    }

    private fun setupUi() {
        binding.beersRv.adapter = adapter.withLoadStateFooter(
            footer = BeerLoadingAdapter { adapter.retry() }
        )
        val afterFilterClickListener = View.OnClickListener {
            viewModel.afterFilterClick()
        }
        val beforeFilterClickListener = View.OnClickListener {
            viewModel.beforeFilterClick()
        }
        binding.afterTxt.setOnClickListener(afterFilterClickListener)
        binding.afterSelectedTxt.setOnClickListener(afterFilterClickListener)
        binding.beforeTxt.setOnClickListener(beforeFilterClickListener)
        binding.beforeSelectedTxt.setOnClickListener(beforeFilterClickListener)
        binding.clearFilterBtn.setOnClickListener{
            viewModel.clearFilterClick()
        }

        adapter.addLoadStateListener { loadState ->
            lifecycleScope.launch {
                viewModel.loadStateChannel.send(loadState)
            }
        }
        binding.retryBtn.setOnClickListener {
            adapter.retry()
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            lifecycleScope.launchWhenCreated {
                beersFlow.collectLatest {
                    adapter.submitData(it)
                }
            }
            brewedAfterText.observe(viewLifecycleOwner, { text ->
                if(text == null)
                    binding.afterSelectedTxt.setText(R.string.filters_unselected)
                else
                    binding.afterSelectedTxt.text = text
            })
            brewedBeforeText.observe(viewLifecycleOwner, { text ->
                if(text == null)
                    binding.beforeSelectedTxt.setText(R.string.filters_unselected)
                else
                    binding.beforeSelectedTxt.text = text
            })
            loadingVisible.observe(viewLifecycleOwner, { visible ->
                binding.loadingContainer.isVisible = visible
            })
            errorVisible.observe(viewLifecycleOwner, { visible ->
                binding.errorContainer.isVisible = visible
            })
            startFilterSelection.observe(viewLifecycleOwner, { event ->
                event.getContentOrNull()?.let { calendar ->
                    val dialog = DatePickerDialog(requireContext(), { _, y, m, _ ->
                        viewModel.onStartPeriodFilterSelected(y, m)
                    }, calendar.get(Calendar.YEAR), Calendar.MONTH, Calendar.DAY_OF_MONTH)
                    dialog.show()
                }
            })
            endFilterSelection.observe(viewLifecycleOwner, { event ->
                event.getContentOrNull()?.let { calendar ->
                    val dialog = DatePickerDialog(requireContext(), { _, y, m, _ ->
                        viewModel.onEndPeriodFilterSelected(y, m)
                    }, calendar.get(Calendar.YEAR), Calendar.MONTH, Calendar.DAY_OF_MONTH)
                    dialog.show()
                }
            })
            navigationDetail.observe(viewLifecycleOwner, { event ->
                event.getContentOrNull()?.let { beer ->
                    val action = BeerListFragmentDirections.actionBeerDetailItems(beer)
                    findNavController().navigate(action)
                }
            })
        }
    }

    private fun onItemCLick(beer: Beer) {
        viewModel.onBeerItemClick(beer)
    }
}