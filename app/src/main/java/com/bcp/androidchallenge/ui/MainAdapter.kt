package com.bcp.androidchallenge.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import com.bcp.androidchallenge.core.BaseViewHolder
import com.bcp.androidchallenge.data.model.ExchangeRate
import com.bcp.androidchallenge.databinding.TRowBinding

/**
 * Created by David Hernandez on 03 July 2020
 */

class MainAdapter(
    private val context: Context,
    private val itemClickListener: OnTClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var exchangeRateList = listOf<ExchangeRate>()

    interface OnTClickListener {
        fun onCClick(exchangeRate: ExchangeRate, position: Int)
    }

    fun setCocktailList(exchangeRateList: List<ExchangeRate>) {
        this.exchangeRateList = exchangeRateList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = TRowBinding.inflate(LayoutInflater.from(context), parent, false)

        val holder = MainViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
            itemClickListener.onCClick(exchangeRateList[position], position)
        }

        return holder
    }

    override fun getItemCount(): Int = exchangeRateList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(exchangeRateList[position])
        }
    }

    private inner class MainViewHolder(
        val binding: TRowBinding
    ) : BaseViewHolder<ExchangeRate>(binding.root) {
        override fun bind(item: ExchangeRate) = with(binding) {


            txtTitulo.text = item.currency
            txtDescripcion.text = item.purchasevalue
        }
    }
}