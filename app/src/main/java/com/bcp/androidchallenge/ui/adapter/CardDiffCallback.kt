package com.bcp.androidchallenge.ui.cards.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bcp.androidchallenge.ui.adapter.model.Card

class CardDiffCallback: DiffUtil.ItemCallback<Card>() {

    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem == newItem

}