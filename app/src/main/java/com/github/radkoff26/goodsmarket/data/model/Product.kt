package com.github.radkoff26.goodsmarket.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Product(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("title")
    val title: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("price")
    val price: Int = 0,
    @SerialName("discountPercentage")
    val discountPercentage: Float = 0F,
    @SerialName("rating")
    val rating: Float = 0F,
    @SerialName("category")
    val category: String = "",
    @SerialName("thumbnail")
    val thumbnailUrl: String = "",
    @SerialName("images")
    val images: List<String> = emptyList()
)
