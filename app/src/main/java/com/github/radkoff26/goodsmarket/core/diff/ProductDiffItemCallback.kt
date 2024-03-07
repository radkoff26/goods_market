package com.github.radkoff26.goodsmarket.core.diff

import androidx.recyclerview.widget.DiffUtil
import com.github.radkoff26.goodsmarket.data.model.Product

object ProductDiffItemCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}