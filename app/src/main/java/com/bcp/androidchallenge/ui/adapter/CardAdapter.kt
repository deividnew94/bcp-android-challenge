package com.bcp.androidchallenge.ui.cards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bcp.androidchallenge.databinding.ItemCardBinding
import com.bcp.androidchallenge.ui.adapter.model.Card

class CardAdapter: ListAdapter<Card, CardAdapter.CardViewHolder>(CardDiffCallback()) {

    var onItemClicked: ((Int, Card) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder = CardViewHolder.from(parent)
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) = holder.bind(getItem(position), onItemClicked)



    class CardViewHolder private constructor(val binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Card, onItemClicked: ((Int, Card) -> Unit)?) {

            binding.category = item
            binding.root.setOnClickListener{ onItemClicked?.invoke(adapterPosition, item) }
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): CardViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCardBinding.inflate(layoutInflater, parent, false)
                return CardViewHolder(binding)
            }

        }

    }

}