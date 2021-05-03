package com.bcp.androidchallenge.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bcp.androidchallenge.R
import com.bcp.androidchallenge.core.Resource
import com.bcp.androidchallenge.data.model.ExchangeRate
import com.bcp.androidchallenge.databinding.FragmentMainBinding
import com.bcp.androidchallenge.presentation.MainViewModel
import com.bcp.androidchallenge.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main),
    MainAdapter.OnTClickListener {
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var mainAdapter: MainAdapter
    private var exchangeRate: ExchangeRate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mainAdapter = MainAdapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMainBinding.bind(view)

        viewModel.fetchExchangeRateList.observe(viewLifecycleOwner, Observer { result ->
            binding.progressBar.showIf { result is Resource.Loading }

            when (result) {
                is Resource.Loading -> {
                    binding.emptyContainer.root.hide()
                }
                is Resource.Success -> {
                    if (result.data.isEmpty()) {
                        binding.emptyContainer.root.show()
                        findNavController().navigate(
                            MainFragmentDirections.actionMainFragmentToHomeFragment(
                                exchangeRate
                            )
                        )
                        return@Observer
                    }
                    mainAdapter.setCocktailList(result.data)
                    binding.emptyContainer.root.hide()

                }
                is Resource.Failure -> {
                    showToast("Ocurrió un error al traer los datos ${result.exception}")
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favoritos -> {
                findNavController().navigate(R.id.action_mainFragment_to_homeFragment)
                false
            }
            else -> false
        }
    }

    override fun onCClick(exchangeRate: ExchangeRate, position: Int) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                exchangeRate
            )
        )
    }
}