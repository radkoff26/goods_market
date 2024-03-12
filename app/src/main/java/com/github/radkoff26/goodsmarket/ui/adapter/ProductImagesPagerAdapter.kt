package com.github.radkoff26.goodsmarket.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.github.radkoff26.goodsmarket.R

class ProductImagesPagerAdapter : RecyclerView.Adapter<ProductImagesPagerAdapter.ViewHolder>() {
    private var imageUrls: List<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_image, parent, false)
        )

    override fun getItemCount(): Int = imageUrls.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        holder.bind(imageUrl)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(newImageUrls: List<String>) {
        if (imageUrls != newImageUrls) {
            imageUrls = newImageUrls
            // No need for smooth data change since data is static
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: SimpleDraweeView = view.findViewById(R.id.product_image)

        fun bind(imageUrl: String) {
            image.setImageURI(imageUrl)
        }
    }
}