package com.github.radkoff26.goodsmarket.data.dto

import com.github.radkoff26.goodsmarket.data.model.Product
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ProductsResponse(
    @SerialName("products")
    val products: List<Product>,
    @SerialName("total")
    val total: Int,
    @SerialName("skip")
    val skip: Int,
    @SerialName("limit")
    val limit: Int
)
