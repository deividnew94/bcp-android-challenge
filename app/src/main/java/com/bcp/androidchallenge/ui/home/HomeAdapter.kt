package com.bcp.androidchallenge.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import com.bcp.androidchallenge.core.BaseViewHolder
import com.bcp.androidchallenge.data.model.ExchangeRate
import com.bcp.androidchallenge.databinding.TRowBinding
import com.bumptech.glide.Glide

/**
 * Created by David Hernandez on 08 August 2020
 */
class HomeAdapter(
    private val context: Context,
    private val itemClickListener: OnCClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var externalRateList = listOf<ExchangeRate>()

    interface OnCClickListener {
        fun onCClick(cocktail: ExchangeRate, position: Int)

        fun onCLongClick(cocktail: ExchangeRate, position: Int)
    }

    fun setCList(cList: List<ExchangeRate>) {
        this.externalRateList = cList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = TRowBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = MainViewHolder(itemBinding)

        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION }
                ?: return@setOnClickListener

            itemClickListener.onCClick(externalRateList[position], position)
        }

        holder.itemView.setOnLongClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION }
                ?: return@setOnLongClickListener true

            itemClickListener.onCLongClick(externalRateList[position], position)

            return@setOnLongClickListener true
        }

        return holder
    }

    override fun getItemCount(): Int = externalRateList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(externalRateList[position])
        }
    }

    private inner class MainViewHolder(
        private val binding: TRowBinding
    ) : BaseViewHolder<ExchangeRate>(binding.root) {
        override fun bind(item: ExchangeRate) = with(binding) {
            Glide.with(context)
                .load(item.exchangeRateId)
                .centerCrop()
                .into(imgC)

            txtTitulo.text = item.currency
            txtDescripcion.text = item.purchasevalue
        }
    }
}