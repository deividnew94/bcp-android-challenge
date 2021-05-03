package com.bcp.androidchallenge.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bcp.androidchallenge.R
import com.bcp.androidchallenge.core.Resource
import com.bcp.androidchallenge.data.model.ExchangeRate
import com.bcp.androidchallenge.databinding.HomeFragmentBinding
import com.bcp.androidchallenge.presentation.MainViewModel
import com.bcp.androidchallenge.ui.details.DetailsFragmentArgs
import com.bcp.androidchallenge.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment),
    HomeAdapter.OnCClickListener {
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var favoritesAdapter: HomeAdapter
    private lateinit var result: List<ExchangeRate>
    private var exchangeRate: ExchangeRate? = null

    var count:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = HomeAdapter(requireContext(), this)

        requireArguments().let {
            DetailsFragmentArgs.fromBundle(it).also { args ->
                exchangeRate = args.drink
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = HomeFragmentBinding.bind(view)


        viewModel.getFavoriteExchangeRate().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if (result.data.isEmpty()) {
                        return@Observer
                    }
                    this.result = result.data

                    if (exchangeRate == null) {
                        binding.textTitle.text = this.getString(R.string.login_desc,result.data.get(0).purchasevalue,result.data.get(0).salevalue)
                    } else {
                        binding.textTitle.text = this.getString(R.string.login_desc,exchangeRate!!.purchasevalue,exchangeRate!!.salevalue)

                        binding.buttonFilter.text = this.getString(R.string.action_filtersDol)
                        binding.buttonFilter2.text = this.getString(R.string.action_filtersEur)
                        count = exchangeRate!!.exchangeRateId.toInt() -1
                    }
                }
                is Resource.Failure -> {
                    showToast("An error occurred ${result.exception}")
                }
            }
        })

        binding.floatingActionButton.setOnClickListener {

            if (exchangeRate == null) {
                if (count == 0) {
                    count = 1
                    binding.buttonFilter.text = this.getString(R.string.action_filtersSol)
                    binding.buttonFilter2.text = this.getString(R.string.action_filtersDol)
                } else {
                    count = 0
                    binding.buttonFilter.text = this.getString(R.string.action_filtersDol)
                    binding.buttonFilter2.text = this.getString(R.string.action_filtersSol)
                }

                if (binding.edittextSearch.text.toString() != "" && binding.edittextSearch.text.toString() != "0.0") {
                    var str: String = "" + binding.edittextSearch.text.toString()
                        .toDouble() * result.get(count).purchasevalue.toDouble()
                    binding.edittextSearch2.setText(str)
                } else {
                    binding.edittextSearch.setText(this.getString(R.string.search_count))
                    binding.edittextSearch2.setText(this.getString(R.string.search_count))
                }
                binding.textTitle.text = this.getString(
                    R.string.login_desc,
                    result.get(count).purchasevalue,
                    result.get(count).salevalue
                )
            } else {
                if (count == 1) {
                    count = exchangeRate!!.exchangeRateId.toInt() -1
                    binding.buttonFilter.text = this.getString(R.string.action_filtersDol)
                    binding.buttonFilter2.text = this.getString(R.string.action_filtersEur)
                } else {
                    count = 1
                    binding.buttonFilter.text = this.getString(R.string.action_filtersEur)
                    binding.buttonFilter2.text = this.getString(R.string.action_filtersDol)
                }

                if (binding.edittextSearch.text.toString() != "" && binding.edittextSearch.text.toString() != "0.0") {
                    var str: String = "" + binding.edittextSearch.text.toString()
                        .toDouble() * result.get(count).purchasevalue.toDouble()
                    binding.edittextSearch2.setText(str)
                } else {
                    binding.edittextSearch.setText(this.getString(R.string.search_count))
                    binding.edittextSearch2.setText(this.getString(R.string.search_count))
                }
                binding.textTitle.text = this.getString(
                    R.string.login_desc,
                    result.get(count).purchasevalue,
                    result.get(count).salevalue
                )
            }
        }
        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
    }

    override fun onCClick(cocktail: ExchangeRate, position: Int) {

    }

    override fun onCLongClick(cocktail: ExchangeRate, position: Int) {
    }
}