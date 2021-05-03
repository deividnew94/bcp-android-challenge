package com.bcp.androidchallenge.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bcp.androidchallenge.R
import com.bcp.androidchallenge.core.Resource
import com.bcp.androidchallenge.data.model.ExchangeRate
import com.bcp.androidchallenge.databinding.FragmentDetailsBinding
import com.bcp.androidchallenge.presentation.MainViewModel
import com.bcp.androidchallenge.ui.adapter.model.Card
import com.bcp.androidchallenge.ui.cards.adapter.CardAdapter
import com.bcp.androidchallenge.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var exchangeRate: ExchangeRate

    private var isCocktailFavorited: Boolean? = null
    private lateinit var cardAdapter: CardAdapter
    lateinit var binding: FragmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun loadListCards(cards: List<Card>) {

        if (::cardAdapter.isInitialized) {
            cardAdapter.submitList(cards)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)

        setupView()
        fun updateButtonIcon() {

        }

        viewLifecycleOwner.lifecycleScope.launch {
            isCocktailFavorited = false
            updateButtonIcon()
        }
    }
    // region setupView

    private fun setupView() {

        if (::binding.isInitialized) {

            viewModel.getFavoriteExchangeRate().observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        if (result.data.isEmpty()) {
                            return@Observer
                        }

                        setupCardsAdapter()
                        loadListCards(listOf(
                            Card("1",getString(R.string.card_european),this.getString(R.string.subtitle_desc,result.data.get(2).purchasevalue, getString(R.string.currency_european)),R.drawable.flag_european,result.data.get(2)),
                            Card("2",getString(R.string.card_eeuu),this.getString(R.string.subtitle_desc,result.data.get(3).purchasevalue, getString(R.string.currency_eeuu)),R.drawable.flag_eeuu,result.data.get(3)),
                            Card("3",getString(R.string.card_japan),this.getString(R.string.subtitle_desc,result.data.get(4).purchasevalue, getString(R.string.currency_japan)),R.drawable.flag_japon,result.data.get(4)),
                            Card("4",getString(R.string.card_uk),this.getString(R.string.subtitle_desc,result.data.get(5).purchasevalue, getString(R.string.currency_uk)),R.drawable.flag_unitedkingdom,result.data.get(5)),
                            Card("5",getString(R.string.card_sw),this.getString(R.string.subtitle_desc,result.data.get(6).purchasevalue, getString(R.string.currency_sw)),R.drawable.flag_switzerland,result.data.get(6)),
                            Card("6",getString(R.string.card_can),this.getString(R.string.subtitle_desc,result.data.get(7).purchasevalue, getString(R.string.currency_can)),R.drawable.flag_canada,result.data.get(7))
                        ))
                    }
                    is Resource.Failure -> {
                        showToast("An error occurred ${result.exception}")
                    }
                }
            })
        }

    }

    private fun setupCardsAdapter() {
        cardAdapter = CardAdapter()

        cardAdapter.onItemClicked = { _, categoryItem ->
            
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailFragmentToHomeFragment(
                    categoryItem.exchangeRate
                )
            )
        }
        binding.recyclerviewCards.adapter = cardAdapter
    }
}