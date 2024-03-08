package com.github.radkoff26.goodsmarket.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.radkoff26.goodsmarket.R
import com.github.radkoff26.goodsmarket.core.diff.ProductDiffItemCallback
import com.github.radkoff26.goodsmarket.data.model.Product
import com.github.radkoff26.goodsmarket.databinding.ItemProductBinding

class ProductsListAdapter :
    PagingDataAdapter<Product, ProductsListAdapter.ProductViewHolder>(ProductDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position) ?: Product()
        holder.bind(product)
    }

    inner class ProductViewHolder(view: View) : ViewHolder(view) {
        private val binding: ItemProductBinding = ItemProductBinding.bind(view)

        fun bind(product: Product) {
            with(binding) {
                productPrice.text = root.resources.getString(R.string.price, product.price)
                productDiscount.text = "-${product.discountPercentage.toInt()}%"
                productTitle.text = product.title
                description.text = product.description
                if (product.thumbnailUrl != "") {
                    productImage.setImageURI(product.thumbnailUrl)
                }
            }
        }
    }
}